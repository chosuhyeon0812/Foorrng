package com.d205.foorrng.operationInfo.repository;

import com.d205.foorrng.operationInfo.OperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OperationInfoRepository extends JpaRepository<OperationInfo, Long>, OperationInfoRepositoryCustom {
    Optional<List<OperationInfo>> findAllByMarkId(Long markId);

}
