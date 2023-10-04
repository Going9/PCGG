package com.ssafy.pcgg.domain.share.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorMarkInfoDto {
	Integer mark;
	Boolean isAuthor;
}
