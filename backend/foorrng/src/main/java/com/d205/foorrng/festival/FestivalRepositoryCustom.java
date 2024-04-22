package com.d205.foorrng.festival;

import java.util.List;
import java.util.Optional;

public interface FestivalRepositoryCustom {
    List<Festival> findAllByPeriod(String period);
}
