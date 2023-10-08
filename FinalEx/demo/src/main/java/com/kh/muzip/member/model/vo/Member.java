package com.kh.muzip.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	private int userNo;//사용자넘버
	private String userId;//사용자 아이디
	private String userPwd;
	private String userName;
	private String email;
	private Date enrollDate;
	private String userInfo;//내소개
	private String backMuNo;//배경음악
	private int membershipNo;
	private String status;
}
