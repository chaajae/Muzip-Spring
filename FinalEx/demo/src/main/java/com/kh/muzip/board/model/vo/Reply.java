package com.kh.muzip.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private String replyNo;
	private String userNo;
	private String refBno;
	private String replyContent;
	private Date createDate;
	private String status;
}
