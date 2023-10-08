package com.kh.muzip.music.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.muzip.music.model.vo.Music;
import com.kh.muzip.music.model.vo.MusicFile;
import com.kh.muzip.music.model.vo.Playlist;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MusicDao {
	
	@Autowired
	private SqlSessionTemplate session;
	
	public ArrayList<Playlist> selectPlaylist(int userNo){
		ArrayList<Playlist> playlist = new ArrayList<Playlist>();
		List<Playlist> list = session.selectList("musicMapper.selectPlaylist", userNo);
		for(int i = 0; i < list.size(); i++) {
			playlist.add(list.get(i));
		}
		return playlist;
	}
	
	public List<String> getGenre(String userNo){
		return session.selectList("musicMapper.getGenre", userNo);
	}
	public ArrayList<ArrayList<Music>> selectRecommendList(List<String> genreList){
		ArrayList<Music> genreArrayList = new ArrayList<Music>();
		if(genreList.size() == 0) {			
			List<Music> genreRecommendList = session.selectList("musicMapper.nonGenreRecommendSongs");
			for(int i = 0; i < genreRecommendList.size(); i++) {
				genreArrayList.add(genreRecommendList.get(i));
			}
		}else {
			List<Music> genreRecommendList = session.selectList("musicMapper.genreRecommendSongs", genreList);
			for(int i = 0; i < genreRecommendList.size(); i++) {
				genreArrayList.add(genreRecommendList.get(i));
			}
		}
		
		ArrayList<Music> hourArrayList = new ArrayList<Music>();
		List<Music> hourList = session.selectList("musicMapper.hourRecommendSongs");
		for(int i = 0; i < hourList.size(); i++) {
			hourArrayList.add(hourList.get(i));
		}
		ArrayList<Music> latestArrayList = new ArrayList<Music>();
		List<Music> latestList = session.selectList("musicMapper.latestRecommendSongs");
		for(int i = 0; i < latestList.size(); i++) {
			latestArrayList.add(latestList.get(i));
		}
		ArrayList<ArrayList<Music>> recommendList = new ArrayList<ArrayList<Music>>();
		recommendList.add(genreArrayList);
		recommendList.add(hourArrayList);
		recommendList.add(latestArrayList);
		
		return recommendList;
	}
	
	public String insertMusic(Music m) {
		int result = 0;
		String resultString = "";
		result = session.insert("musicMapper.insertMusic", m);
		if(result > 0) {
			// selectKey 이용하여 셋팅한 musicNo값을 가져옴
			resultString = m.getMusicNo();
		}
		return resultString;
	}
	
	public int insertMusicFile(MusicFile musicFile) {
		return session.insert("musicMapper.insertMusicFile", musicFile);
	}
	
	public MusicFile selectOneMusicFile(String musicNo) {
		return session.selectOne("musicMapper.selectOneMusicFile", musicNo);
	}
	
	public int updateMusic(Music m) {
		return session.update("musicMapper.updateMusic", m);
	}
	
	public int updateMusicFile(MusicFile musicFile) {
		return session.update("musicMapper.updateMusicFile", musicFile);
	}
	
	public int deleteMusic(String musicNo) {
		int result = 1;
		result *= session.update("musicMapper.deleteMusic", musicNo);
		result *= session.update("musicMapper.deleteMusicFile", musicNo);
		return result;
	}

	public int insertPlaylist(String playlistName, String userNo) {
		Map<String, String> listMap = new HashMap<>();
		listMap.put("playlistName", playlistName);
		listMap.put("userNo", userNo);
		return session.insert("musicMapper.insertPlaylist", listMap);
	}
	
	public int deletePlaylist(String playlistNo) {
		session.delete("musicMapper.deleteSongsOfPlaylist", playlistNo);
		return session.delete("musicMapper.deletePlaylist", playlistNo);
	}
	
	public Music selectOneMusic(String musicNo) {
		return session.selectOne("musicMapper.selectOneMusic", musicNo);
	}
	
	public int addPlaylistSong(String playlistNo, String musicNo) {
		Map<String, String> listMap = new HashMap<>();
		listMap.put("playlistNo", playlistNo);
		listMap.put("musicNo", musicNo);
		
		String playlistSongs = session.selectOne("musicMapper.selectPlaylistOneSongs", listMap);
		if(musicNo.equals(playlistSongs)) {
			return 0;
		}else {			
			return session.insert("musicMapper.addPlaylistSong", listMap);
		}
	}
	
	public int removePlaylistSong(String playlistNo, String musicNo) {
		Map<String, String> listMap = new HashMap<>();
		listMap.put("playlistNo", playlistNo);
		listMap.put("musicNo", musicNo);
		return session.delete("musicMapper.removePlaylistSong", listMap);
	}
	
	public int increaseCount(String musicNo) {
		return session.update("musicMapper.increaseCount", musicNo);
	}
	public void resetHourCount() {
		session.update("musicMapper.resetHourCount");
	}
	public void resetDailyCount() {
		session.update("musicMapper.resetDailyCount");
	}
	
	public void insertKeyword(String keyword) {
		session.insert("musicMapper.insertKeyword", keyword);
	}
	
	public List<Music> searchMusic(String keyword){
		session.insert("musicMapper.insertKeyword", keyword);
		return session.selectList("musicMapper.searchMusic", keyword);
	}
	
	public List<String> selectRanking(){
	    return session.selectList("musicMapper.selectRanking");
	}
}
