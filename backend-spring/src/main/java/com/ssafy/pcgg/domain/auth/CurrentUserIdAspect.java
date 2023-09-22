package com.ssafy.pcgg.domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.security.Key;
import java.util.Objects;

@Aspect
@Component
public class CurrentUserIdAspect {

    private final String secret;
    private final ParameterNameDiscoverer parameterNameDiscoverer;

    public CurrentUserIdAspect(@Value("${jwt.secret}") String secret, ParameterNameDiscoverer parameterNameDiscoverer) {
        this.secret = secret;
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    @Before("@annotation(currentUserId) && args(userIdDto, request, ..)")
    public void currentUserId(JoinPoint joinPoint, UserIdDto userIdDto, HttpServletRequest request, CurrentUserId currentUserId) {
        String token = Objects.requireNonNull(request.getHeader("Authorization")).replace("Bearer ", "");

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String claimValue = claims.get(currentUserId.value()).toString();

        Method method = getMethod(joinPoint);
        if (method != null) {
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                String paraName = parameterNames[i];
                if(paraName != null && paraName.equals(currentUserId.value())) {
                    userIdDto.setUserId(Long.valueOf(claimValue));
                    return;
                }
            }
        }

//        for (Object arg : joinPoint.getArgs()) {
//            if (arg.getClass().isAssignableFrom(String.class)) {
//                String paramName = ((currentUserId) joinPoint.getStaticPart().).value();
//                if (paramName.equals(arg)) {
//                    return;
//                }
//            }
//        }
//
//        ((ServletRequestAttributes) RequestContextHolder
//                .currentRequestAttributes())
//                .getRequest()
//                .setAttribute(currentUserId.value(), claimValue);
    }

    private Method getMethod(JoinPoint joinPoint) {
        try {
            Class<?> targetClass = joinPoint.getTarget().getClass();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = targetClass.getMethod(signature.getName(), signature.getParameterTypes());
            return AnnotatedElementUtils.findMergedAnnotation(method, CurrentUserId.class) != null ? method : null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
