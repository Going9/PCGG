package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.oauth.SocialType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<UserEntity> findByNickname(String nickname);
}
