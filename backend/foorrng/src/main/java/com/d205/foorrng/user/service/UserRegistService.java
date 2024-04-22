package com.d205.foorrng.user.service;


import com.d205.foorrng.user.dto.RegistDto;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@Getter @Setter
@RequiredArgsConstructor
public class UserRegistService {

    @Value("${externalService.apiKey}")
    private String apiKey;

    private final UserRepository userRepository;

    public boolean checkBusinessNumber(RegistDto businessNumber) {
        //  금요일 테스트용으로 중복 허용
//        if (userRepository.findByBusinessNumber(businessNumber.getBusinessNumber()) != null) {
//            return false;
//        }

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/status";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Infuser " + apiKey);

        List<String> numberContainer = new ArrayList<>();
        numberContainer.add(businessNumber.getBusinessNumber());

        JSONObject requestBody = new JSONObject();
        requestBody.put("b_no", numberContainer);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        JSONObject jsonResponse = new JSONObject(responseBody);

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        JSONObject dataObject = dataArray.getJSONObject(0);

        String result = dataObject.get("b_stt").toString();
        if (result.equals("계속사업자")) {
            User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
            user.addBusinessNumber(businessNumber.getBusinessNumber());
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public void testMethod() {
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        user.addBusinessNumber("0123456789");
        userRepository.save(user);
    }
}
