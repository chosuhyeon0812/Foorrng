package com.d205.foorrng.foodtruck.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.entity.Menu;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.foodtruck.repository.MenuRepository;
import com.d205.foorrng.foodtruck.request.MenuRequestDto;
import com.d205.foorrng.foodtruck.response.MenuResDto;
import com.d205.foorrng.util.S3Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final FoodtrucksRepository foodtrucksRepository;
    private final S3Image imageSave;

    @Override
    public MenuResDto createMenu(Long foodtrucks_seq, MenuRequestDto menuResquestDto, MultipartFile picture) throws IOException {
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtrucks_seq)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));;

        // 메뉴 저장하기
        Menu menu = Menu.builder()
                .name(menuResquestDto.getName())
                .price(menuResquestDto.getPrice())
                .foodtrucks(foodtrucks)
                .build();

        // 이미지 s3에 저장하기
        String imgUrl = "";
        if(picture != null) {
            String imgName = "menuIMG/" + menuResquestDto.getName() + ".png"; // 확장명
            String dir = "/menuIMG";
            imgUrl = imageSave.saveImageS3(picture, imgName,dir);
        }
        menu.changePicture(imgUrl);
        menuRepository.save(menu);

        return MenuResDto.fromEntity(menu);
    }

    // 모든 메뉴 조회하기
    @Override
    public List<MenuResDto> findAllMenus() {
        return menuRepository.findAll()
                .stream().map(MenuResDto::fromEntity).toList();
    }

    @Override
    public List<MenuResDto> getMenus(Long foodtrucks_seq) {
        Foodtrucks foodtruck = foodtrucksRepository.findById(foodtrucks_seq)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        // 해당 푸드트럭의 모든 메뉴 가져오기
        List<Menu> menus = menuRepository.findAllByFoodtrucks_Id(foodtruck.getId());

        // Menu 엔티티 리스트를 MenuResDto 리스트로 변환
        return menus.stream()
                .map(menu -> new MenuResDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getPrice(),
                        menu.getPicture(),
                        menu.getFoodtrucks().getId()))
                .collect(Collectors.toList());
    }
    @Override
    public MenuResDto updateMenu(Long menu_seq, MenuRequestDto menuRequestDto, MultipartFile picture) throws IOException {

        // 해당 푸드 트럭에서 수정할 메뉴 선택
        Menu menu = menuRepository.findById(menu_seq)
                .orElseThrow(() -> new Exceptions(ErrorCode.MENU_NOT_FOUND));

        // 메뉴 엔티티 수정
        menu.changeName(menuRequestDto.getName());
        menu.changePrice(menuRequestDto.getPrice());

        // 이미지 s3에 저장하기
        String imgUrl = "";
        if (picture != null) {
            String imgName = "menuIMG/" + menu.getId() + menuRequestDto.getName() + ".png"; // 확장명
            String dir = "/menuIMG";
            imgUrl = imageSave.saveImageS3(picture, imgName, dir);
            menu.changePicture(imgUrl);
        }

        // 메뉴 저장하기
        menuRepository.save(menu);

        return MenuResDto.fromEntity(menu);
    }

    @Override
    public int deleteMenu(Long menu_seq) throws IOException {
        Menu menu = menuRepository.findById(menu_seq)
                .orElseThrow(() -> new Exceptions(ErrorCode.MENU_NOT_FOUND));

        menuRepository.deleteById(menu_seq);
        return 1;
    }
}
