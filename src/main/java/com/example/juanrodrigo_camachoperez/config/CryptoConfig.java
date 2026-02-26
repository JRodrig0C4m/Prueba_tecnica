package com.example.juanrodrigo_camachoperez.config;

import com.example.juanrodrigo_camachoperez.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class CryptoConfig {

    @Bean
    public CryptoUtil cryptoUtil(@Value("${app.crypto.aesKeyBase64}") String aesKeyBase64) {
        byte[] key = Base64.getDecoder().decode(aesKeyBase64);
        return new CryptoUtil(key);
    }
}
