package com.d205.foorrng.mark.dto;

import com.d205.foorrng.operationInfo.OperationInfo;
import com.d205.foorrng.operationInfo.dto.OperationInfoDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter

public class MarkReqDto {

    @NotNull
    private MarkDto markDto;

    @Nullable
    private OperationInfoDto operationInfoDto;


}
