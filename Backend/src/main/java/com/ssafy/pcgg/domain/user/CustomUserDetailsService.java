package com.ssafy.pcgg.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDtailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createUser(String email, UserEntity userEntity) {
        if (!userEntity.isActivated()) {
            throw new RuntimeException(email + " -> 활성화되어 있지 않습니다.");
        }

        String email2 = userEntity.getEmail();
        String password = userEntity.getPassword();
        List<GrantedAuthority> grantedAuthorities = userEntity.getAuthorities().stream()
                .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(email2, password, grantedAuthorities);
    }
}
