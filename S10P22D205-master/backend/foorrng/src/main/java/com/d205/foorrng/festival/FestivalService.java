package com.d205.foorrng.festival;

import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;


@Service
@Getter @Setter
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    @Transactional
    public List<Festival> searchAllFestival() {
        return festivalRepository.findAll();
    }


    @Transactional
    public List<Festival> searchFestivalInThisMonth() {
        String thisMonth = String.valueOf(Integer.parseInt(LocalDate.now(ZoneId.of("Asia/Seoul")).toString().substring(5, 7)));

        List<Festival> festivalList = festivalRepository.findAllByPeriod(thisMonth);

        return festivalList;
    }


    @Transactional
    public Festival searchFestival(Long festivalId) {
        return festivalRepository.findByFestivalId(festivalId)
                .orElseThrow(() -> new Exceptions(ErrorCode.FESTIVAL_NOT_EXIST));
    }



}
