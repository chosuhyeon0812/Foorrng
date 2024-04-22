package com.d205.foorrng.article;

import jakarta.persistence.AttributeConverter;

public class CryptoConverter implements AttributeConverter<String,String> {


    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            return CryptoUtil.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String convertToEntityAttribute(String attribute) {
        if (attribute == null) return null;
        try {
            return CryptoUtil.decrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
