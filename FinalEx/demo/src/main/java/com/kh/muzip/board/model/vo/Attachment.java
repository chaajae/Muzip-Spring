package com.kh.muzip.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
	private String fileNo;//파일 고유번호
	private String userNo;//파일을 올린 유저의 고유번호
	private String boardNo;//파일을 올린 게시판 번호 프로필 보드넘버는 999로 임의로 만든다.
	private String originName;//파일의 저장하기전 원래 이름
	private String changeName;//파일을 저장하면서 바꾼 이름
	private String filePath;//파일이 위치한 경로
	private Date uploadDate;//파일을 올린 날짜
	private int fileLevel;//파일의 고유 레벨( 프로필 이미지 파일레벨 = 1, 배경이미지 파일레벨은 = 2)
	private String status;//파일을 현재 사용하는지 상태 표시(Y/N)
}
