package com.d205.foorrng.foodtruck.service;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.request.MenuRequestDto;
import com.d205.foorrng.foodtruck.response.MenuResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MenuService {

    // 메뉴 생성
    MenuResDto createMenu(Long foodtrucks_seq, MenuRequestDto menuResquestDto, MultipartFile picture) throws IOException;

    // 모든 메뉴 조회
    List<MenuResDto> findAllMenus();

    // 메뉴 조회(해당 푸드트럭)
    List<MenuResDto> getMenus(Long foodtrucks_seq);

    // 메뉴 수정
    MenuResDto updateMenu(Long menu_seq, MenuRequestDto menuRequestDto, MultipartFile picture) throws IOException;

    // 메뉴 삭제
    int deleteMenu(Long menu_seq) throws IOException;


}
