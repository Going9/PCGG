package com.ssafy.pcgg.domain.auth;

import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long expireTimeMs;
    private Key key;
    private UserRepository userRepository;

    public TokenProvider (
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expire-time-sec}") long expireTimeSec,
            UserRepository userRepository) {
        this.secret = secret;
        this.expireTimeMs = expireTimeSec * 1000;   // expireTimeSec = 86400 = 1(1초) * 60(1분) * 60(1시간) * 24(하루)
        this.userRepository = userRepository;
    }

    // secret으로 key를 설정
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        UserEntity userEntity = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)    // Jwts에 AUTHORITIES_KEY의 이름으로 authorities저장
                .claim("userId", userEntity.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Authentication 객체를 반환하는 함수
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("SecurityException or MalformedJwtException");
        } catch (ExpiredJwtException e) {
            System.out.println("ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            System.out.println("UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException");
        }
        return false;
    }
}
