package com.kh.muzip.music.model.vo;

import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

	private String playlistNo;
	private String userNo;
	private String playlistName;
	private String status;
	private ArrayList<Music> playlistSongs;
	
}
