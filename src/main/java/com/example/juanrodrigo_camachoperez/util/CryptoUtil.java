package com.example.juanrodrigo_camachoperez.util;

import com.example.juanrodrigo_camachoperez.exception.BadRequestException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256 using AES/GCM/NoPadding.
 * Stored as: base64(nonce) + ":" + base64(ciphertext+tag)
 */
public class CryptoUtil {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128;
    private static final int NONCE_BYTES = 12;

    private final SecretKey key;
    private final SecureRandom random = new SecureRandom();

    public CryptoUtil(byte[] rawKey32Bytes) {
        if (rawKey32Bytes == null || rawKey32Bytes.length != 32) {
            throw new IllegalArgumentException("AES-256 key must be exactly 32 bytes.");
        }
        this.key = new SecretKeySpec(rawKey32Bytes, "AES");
    }

    public String encrypt(String plainText) {
        if (plainText == null) throw new BadRequestException("password is required");
        try {
            byte[] nonce = new byte[NONCE_BYTES];
            random.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, nonce));
            byte[] ct = cipher.doFinal(plainText.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(nonce) + ":" + Base64.getEncoder().encodeToString(ct);
        } catch (Exception e) {
            throw new BadRequestException("Password encryption failed");
        }
    }

    public String decrypt(String encrypted) {
        try {
            String[] parts = encrypted.split(":");
            if (parts.length != 2) throw new IllegalArgumentException("Invalid encrypted format");

            byte[] nonce = Base64.getDecoder().decode(parts[0]);
            byte[] ct = Base64.getDecoder().decode(parts[1]);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, nonce));
            byte[] pt = cipher.doFinal(ct);

            return new String(pt, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BadRequestException("Password decryption failed");
        }
    }
}
