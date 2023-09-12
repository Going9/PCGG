package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.AuthorityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
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
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "users_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority", referencedColumnName = "authority")})
    private Set<AuthorityEntity> authorities;

    @Builder
    public UserEntity(String email, String password, boolean activated, Set<AuthorityEntity> authorities) {
        this.email = email;
        this.password = password;
        this.activated = activated;
        this.authorities = authorities;
    }
}
