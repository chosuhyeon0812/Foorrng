package com.d205.foorrng.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseBody<T> {
    @Getter
    @Setter
    public static class DataHeader{
        // successCode
        // 0 성공
        // 1 오류
        private int successCode;

        // 출력 코드값
        private String resultCode = "";

        // 결과메세지
        private String resultMessage = "";
    }

    private DataHeader dataHeader = new DataHeader();
    private T dataBody;

    public static <T> BaseResponseBody<T> of(int successCode, T dataBody){
        BaseResponseBody<T> body = new BaseResponseBody<>();
        body.getDataHeader().setSuccessCode(successCode);
        body.setDataBody(dataBody);
        return body;
    }

    public static BaseResponseBody<Object> error(String resultCode, String resultMessage){
        BaseResponseBody<Object> body = new BaseResponseBody<>();
        body.getDataHeader().setSuccessCode(1);
        body.getDataHeader().setResultCode(resultCode);
        body.getDataHeader().setResultMessage(resultMessage);
        return body;
    }

}
