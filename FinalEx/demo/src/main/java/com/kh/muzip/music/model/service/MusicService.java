package com.kh.muzip.music.model.service;

import java.util.ArrayList;
import java.util.List;

import com.kh.muzip.music.model.vo.Music;
import com.kh.muzip.music.model.vo.MusicFile;
import com.kh.muzip.music.model.vo.Playlist;

public interface MusicService {
	ArrayList<Playlist> selectPlaylist(int userNo);
	ArrayList<ArrayList<Music>> selectRecommendList(List<String> genreList);
	List<String> getGenre(String userNo);
	int insertMusic(Music m, MusicFile musicFile, 
					String imageServerFolderPath, String imageWebPath, String mp3ServerFolderPath, String mp3WebPath) throws Exception;
	int updateMusic(Music m, MusicFile musicFile, 
			String imageServerFolderPath, String imageWebPath, String mp3ServerFolderPath, String mp3WebPath) throws Exception;
	int deleteMusic(String musicNo);
	int insertPlaylist(String playlistName, String userNo);
	int deletePlaylist(String playlistNo);
	Music selectOneMusic(String musicNo);
	int addPlaylistSong(String playlistNo, String musicNo);
	int removePlaylistSong(String playlistNo, String musicNo);
	int increaseCount(String musicNo);
	void resetHourCount();
	void resetDailyCount();
	void insertKeyword(String keyword);
	List<Music> searchMusic(String keyword);
	List<String> selectRanking();
}
