package com.kh.muzip.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

	private String userId;//팔로잉한 유저
	private String memberId;//팔로우한 유저
	private String status;//현재 팔로우 상태(Y/N)
	
	
	private int memberNo;//팔로우한 유저의 넘버
	private int userNo;//팔로잉한 유저의 넘버
}
