package gugunava.danil.usermanagementservice.generator;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtGenerator {

    public static Jwt valid() {
        return Jwt
                .withTokenValue("token")
                .header("header", "header")
                .claim("claim", "claim")
                .build();
    }
}
