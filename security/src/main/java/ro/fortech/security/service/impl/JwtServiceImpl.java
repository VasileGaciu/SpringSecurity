package ro.fortech.security.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.fortech.security.model.AuthenticationModel;
import ro.fortech.security.model.TokenModel;
import ro.fortech.security.service.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ro.fortech.security.util.Constants.JWT_SECRET;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDetails user;

    public TokenModel getTokens(AuthenticationModel authenticationModel) {
        UserDetails user = userDetailsService.loadUserByUsername(authenticationModel.getUsername());
        if (!passwordEncoder.matches(authenticationModel.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad Credentials");
        }
        Map<String, Object> claims = new HashMap<>();
        TokenModel tokenModel = new TokenModel();
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + 1000 * 60);
        Date refreshTokenExpiration = new Date(System.currentTimeMillis() + 1000 * 60 * 3);
        String accessToken = generateToken(authenticationModel.getUsername(), claims, accessTokenExpiration);
        String refreshToken = generateToken(authenticationModel.getUsername(), claims, refreshTokenExpiration);
        tokenModel.setAccessToken(accessToken);
        tokenModel.setRefreshToken(refreshToken);
        return tokenModel;
    }

    @Override
    public TokenModel getAccessToken(String token) {
        if(!validateJwtToken(token)){
           throw new BadCredentialsException("Token Invalid");
        }
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + 1000 * 60);
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        String accessToken = generateToken(claims.getSubject(), claims, accessTokenExpiration);
        return new TokenModel(accessToken, token);
    }

    @Override
    public boolean validateJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
            user = userDetailsService.loadUserByUsername(claims.getSubject());
            if (user != null && claims.getExpiration().after(new Date(System.currentTimeMillis()))) {
                return true;
            }

        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
        }
        return false;
    }

    @Override
    public Authentication createAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(), null, user.getAuthorities());
    }

    private String generateToken(String username, Map<String, Object> claims, Date valid) {
        return createToken(claims, username, valid);
    }

    private String createToken(Map<String, Object> claims, String username, Date valid) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(valid)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }
}
