package com.d205.foorrng.mark.service;


import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.mark.Mark;
import com.d205.foorrng.mark.dto.MarkDto;
import com.d205.foorrng.mark.repository.MarkRepository;
import com.d205.foorrng.operationInfo.repository.OperationInfoRepository;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class
MarkService {

    private final UserRepository userRepository;
    private final FoodtrucksRepository foodtrucksRepository;
    private final MarkRepository markRepository;
    private  final OperationInfoRepository operationInfoRepository;

    @Transactional
    public Map<String, Object> createMark(Long foodtruckId, MarkDto markDto) {

        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtruckId)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        List<Mark> foodtruckMarkList = markRepository.findAllByFoodtrucksId(foodtruckId).get();

        if (foodtruckMarkList.size() >= 7) {
            throw new Exceptions(ErrorCode.MARK_OCCUPIED);
        }

        Mark mark = Mark.builder()
                .foodtrucks(foodtrucks)
                .longitude(markDto.getLongitude())
                .latitude(markDto.getLatitude())
                .address(markDto.getAddress())
                .isOpen(false)
                .build();

        markRepository.save(mark);

        Map<String, Object> response = new HashMap<>();

        response.put("foodtruckId", mark.getFoodtrucks().getId());
        response.put("markId", mark.getId());
        response.put("latitude", mark.getLatitude());
        response.put("longitude", mark.getLongitude());

        return response;
    }


    @Transactional
    public List<Mark> searchOwnerMarkList() {

        Foodtrucks foodtrucks = foodtrucksRepository.findByUserUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get()))
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));


        return markRepository.findAllByFoodtrucksId(foodtrucks.getId())
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));
    }


    @Transactional
    public Mark searchMarkDetail(Long markId) {

        return markRepository.findById(markId).orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));
    }




    @Transactional
    public Mark updateMark(Long markId, MarkDto markDto) {

        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));

        mark.update(markDto);


        return markRepository.save(mark);
    }


    @Transactional
    public void removeMark(Long markId) {

        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));

        markRepository.delete(mark);
    }


    @Transactional
    public Mark toggleMark(Long markId) {


        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));

        mark.changeIsOpen();

        return markRepository.save(mark);
    }



}
