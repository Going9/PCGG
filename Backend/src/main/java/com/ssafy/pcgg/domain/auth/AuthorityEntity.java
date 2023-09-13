package com.ssafy.pcgg.domain.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class AuthorityEntity {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}
