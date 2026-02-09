package starwars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import starwars.dto.*;
import starwars.exception.UnauthorizedException;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthenticationService {
    private final Map<String, TokenData> accessTokens = new ConcurrentHashMap<>();
    private final Map<String, TokenData> refreshTokens = new ConcurrentHashMap<>();

    private final long accessTokenExpiry;
    private final long refreshTokenExpiry;

    public AuthenticationService(@Value("${auth.access-token-expiry}") long accessTokenExpiry,
                                 @Value("${auth.refresh-token-expiry}") long refreshTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public LoginResponse login(LoginRequest request) {
        validateRequest(request);

        String username = request.username();
        String accessToken = generateToken();
        String refreshToken = generateToken();

        long now = Instant.now().toEpochMilli();
        TokenData accessTokenData = new TokenData(username, now + accessTokenExpiry);
        TokenData refreshTokenData = new TokenData(username, now + refreshTokenExpiry);

        accessTokens.put(accessToken, accessTokenData);
        refreshTokens.put(refreshToken, refreshTokenData);

        UserInfo user = UserInfo.builder().username(username).build();
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        validateRequest(request);

        String refreshToken = request.refreshToken();

        TokenData tokenData = refreshTokens.get(refreshToken);

        if (tokenData == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        if (isTokenExpired(tokenData)) {
            refreshTokens.remove(refreshToken);
            throw new UnauthorizedException("Refresh token expired");
        }

        String newAccessToken = generateToken();
        long now = Instant.now().toEpochMilli();
        TokenData newAccessTokenData = new TokenData(tokenData.username, now + accessTokenExpiry);

        accessTokens.put(newAccessToken, newAccessTokenData);
        return RefreshTokenResponse.builder().accessToken(newAccessToken).build();
    }

    public void logout(String accessToken) {
        Objects.requireNonNull(accessToken, "accessToken must not be null");

        TokenData tokenData = accessTokens.remove(accessToken);
        if (tokenData != null) {
            refreshTokens
                    .entrySet()
                    .removeIf(entry -> Objects.equals(entry.getValue().username, tokenData.username));
        }
    }

    public String validateAccessToken(String token) {
        if (token == null || token.isBlank()) {
            throw new UnauthorizedException("Access token required");
        }
        TokenData tokenData = accessTokens.get(token);

        if (tokenData == null) {
            throw new UnauthorizedException("Invalid or expired access token");
        }
        if (isTokenExpired(tokenData)) {
            accessTokens.remove(token);
            throw new UnauthorizedException("Access token expired");
        }
        return tokenData.username;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private boolean isTokenExpired(TokenData tokenData) {
        return Instant.now().toEpochMilli() > tokenData.expiryTime;
    }

    private void validateRequest(LoginRequest request) {
        if (request.username() == null || request.username().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    private void validateRequest(RefreshTokenRequest request) {
        if (request.refreshToken() == null || request.refreshToken().isBlank()) {
            throw new UnauthorizedException("Refresh token required");
        }
    }

    private record TokenData(String username, long expiryTime) {
    }
}
