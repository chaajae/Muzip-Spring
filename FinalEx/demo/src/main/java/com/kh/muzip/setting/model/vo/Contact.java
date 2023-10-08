package com.kh.muzip.setting.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contact {
	private int contactNo;
	private int userNo;
	private String userId;
	private String contactTitle;
	private String contactCont;
	private Date contactDate;
	private String adminReply;
	private String status;
}
/*
CONTACT_NO
USER_NO
CONTACT_TITLE
CONTACT_CONT
CONTACT_DATE
ADMIN_REPLY
STATUS
*/