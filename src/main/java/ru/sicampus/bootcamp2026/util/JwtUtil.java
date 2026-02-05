package ru.sicampus.bootcamp2026.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import ru.sicampus.bootcamp2026.config.JwtConfig;
import ru.sicampus.bootcamp2026.exception.RsaKeyInitException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Getter
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private JwtParser jwtParser;

    @PostConstruct// после DI
    void init(){
        try{
            this.privateKey = loadCleanPrivateKey(jwtConfig.getPrivateKey());
            this.publicKey = loadCleanPublicKey(jwtConfig.getPublicKey());
            this.jwtParser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)//установка ключа для проверки на соответствие
                    .build();//создаем экземпляр парсера
            log.info("JWT keys & parser successfully initialized");
        }catch (Exception e){
            log.error("Critical error during RSA keys initialization", e);
            throw new RsaKeyInitException("Error loading keys: " + e);
        }
    }

    private PrivateKey loadCleanPrivateKey(Resource privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] keyBytes = loadAndCleanKey(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey loadCleanPublicKey(Resource publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] keyBytes = loadAndCleanKey(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private byte[] loadAndCleanKey(Resource resource) throws IOException {
        String rawContent = readResourceToString(resource);
        String cleanedContent = clearKeyContent(rawContent);
        return Base64.getDecoder().decode(cleanedContent);
    }

    private String clearKeyContent(String rawContent) {
        if(rawContent.isBlank()){
            throw new RsaKeyInitException("Key content is empty");
        }
        return rawContent
                .replaceAll("-----BEGIN (.*)-----", "")//заголовок ключа
                .replaceAll("-----END (.*)-----", "")//футер ключа
                .replaceAll("\\s", "");//удаление пробелов и переносов строк
    }

    private String readResourceToString(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }


}
