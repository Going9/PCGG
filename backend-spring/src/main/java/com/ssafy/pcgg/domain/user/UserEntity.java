package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.AuthorityEntity;
import com.ssafy.pcgg.domain.auth.oauth.SocialType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String nickname;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean activated;

    // 참조 테이블로 Refactoring 필요
    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "social_id")
    private String socialId;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AuthorityEntity> authorities;

    @Builder
    public UserEntity(String email, String password, String name, String nickname, boolean activated, Set<AuthorityEntity> authorities) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.activated = activated;
        this.authorities = authorities;
    }

    public void reSignup(String password, String name, String nickname) {
        this.activated = true;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public void withdrawal() {
        this.activated = false;
    }
}
