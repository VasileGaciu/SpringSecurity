package ro.fortech.security.service;

import org.springframework.security.core.Authentication;
import ro.fortech.security.model.AuthenticationModel;
import ro.fortech.security.model.TokenModel;

public interface JwtService {

    TokenModel getTokens(AuthenticationModel accountModel);

    boolean validateJwtToken(String token);

    Authentication createAuthentication(String token);

    TokenModel getAccessToken(String token);
}
