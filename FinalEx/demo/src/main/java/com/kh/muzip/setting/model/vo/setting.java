package com.kh.muzip.setting.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class setting {
	
	private int userNo;
	private String autoPlay;
	private String theme;
	private String chatAlarm;
	private String musicAlarm;
	private String commentAlarm;
	
}
