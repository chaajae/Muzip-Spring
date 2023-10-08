package com.kh.muzip.board.model.vo;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private String boardNo;
	private String userNo;
	private String userId;
	private String boardContent;
	private Date createDate;
	private String secret;
	private String status;
	private String musicNo;
	
}
