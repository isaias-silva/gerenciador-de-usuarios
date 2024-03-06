package com.zack.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zack.api.models.UserModel;
import com.zack.api.util.responses.enums.GlobalResponses;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel user) {
        try {
            Date issuedAt = new Date();
            Date expiresAt = new Date(issuedAt.getTime() + 24 * 60 * 60 * 1000);

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

        }
        catch (JWTCreationException e) {

            throw new RuntimeException("erro ao gerar token", e);
        }
    }

    public UUID isValidToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            String idSubject= JWT.require(algorithm).withIssuer("auth")
                    .build()
                    .verify(token).getSubject();

            return UUID.fromString(idSubject);

        } catch (JWTVerificationException e) {

            throw new RuntimeException(GlobalResponses.INVALID_TOKEN.getText());

        }
    }

}
