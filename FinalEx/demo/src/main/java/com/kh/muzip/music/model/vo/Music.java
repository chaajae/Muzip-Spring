package com.kh.muzip.music.model.vo;

import java.sql.Date;

import com.kh.muzip.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Music {

	private String musicNo;
	private String musicTitle;
	private String musicArtist;
	private String musicLyrics;
	private String genre;
	private Date enrollDate;
	private int playCount;
	private int dailyCount;
	private int hourCount;
	private String musicPath;
	private String coverPath;
	private String status;
	
}
