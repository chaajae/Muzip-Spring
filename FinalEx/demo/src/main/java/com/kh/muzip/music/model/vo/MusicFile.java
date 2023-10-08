package com.kh.muzip.music.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicFile {

	private String musicNo;
	private String musicOriginName;
	private String musicChangeName;
	private String musicPath;
	private String coverOriginName;
	private String coverChangeName;
	private String coverPath;
	private String status;
	
}
