package com.kh.muzip.chat.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

	private int chatroomNo;
	private String userId;
	private String chatroomName;
	private String status;
	private String viewMsg;
	private Date viewDate;
}
