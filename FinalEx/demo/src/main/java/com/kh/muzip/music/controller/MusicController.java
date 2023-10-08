package com.kh.muzip.music.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.common.Utils;
import com.kh.muzip.music.model.service.MusicService;
import com.kh.muzip.music.model.vo.Music;
import com.kh.muzip.music.model.vo.MusicFile;
import com.kh.muzip.music.model.vo.Playlist;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user2
 *
 */
@RestController
@Slf4j
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class MusicController {
	
	@Autowired
	private MusicService musicService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/myPlaylist")
	public ArrayList<Playlist> myPlaylist(
			@RequestParam("userNo") String userNo
			){
		
		ArrayList<Playlist> playlist = musicService.selectPlaylist(Integer.parseInt(userNo));
		
		return playlist;
		
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getGenre")
	public List<String> getGenre(
			@RequestParam("userNo") String userNo
			){		
		List<String> genreList = musicService.getGenre(userNo);
		
		return genreList;
		
	}
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/recommendList")
	public ArrayList<ArrayList<Music>> recommendList(
			@RequestBody Map<String, List<String>> requestData
			){
		List<String> genreList = requestData.get("genre");
		ArrayList<ArrayList<Music>> recommendList = musicService.selectRecommendList(genreList);
		
		return recommendList;
	}
	
	@Autowired
	private ServletContext application;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value ="/insertMusic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int insertMusic(
			@RequestParam("cover") MultipartFile cover,
			@RequestParam("music") MultipartFile music,
			@RequestParam("title") String title, 
			@RequestParam("artist") String artist,
			@RequestParam("lyrics") String lyrics,
			@RequestParam("genre") String genre
			){

		Music m = new Music();
		m.setMusicTitle(title);
		m.setMusicArtist(artist);
		m.setMusicLyrics(lyrics);
		m.setGenre(genre);
		
		String imgaeWebPath="/resources/image/";
		String mp3WebPath="/resources/mp3/";
		String imageServerFolderPath = application.getRealPath(imgaeWebPath);
		String mp3ServerFolderPath = application.getRealPath(mp3WebPath);
		
		// 디렉토리 생성, 해당 디렉토리가 존재하지 않는다면 생성
		File dir1 = new File(imageServerFolderPath);
		File dir2 = new File(mp3ServerFolderPath);
		if(!dir1.exists()) dir1.mkdirs();
		if(!dir2.exists()) dir2.mkdirs();
		
		MusicFile musicFile = new MusicFile();
		
		if(!cover.isEmpty()) {			
			// 파일명 재정의 + 저장
			String changeName = Utils.saveFile(cover, imageServerFolderPath);
			musicFile.setCoverChangeName(changeName);
			musicFile.setCoverOriginName(cover.getOriginalFilename());
		};
		if(!music.isEmpty()) {
			String changeName = Utils.saveFile(music, mp3ServerFolderPath);
			musicFile.setMusicChangeName(changeName);
			musicFile.setMusicOriginName(music.getOriginalFilename());
		}
			
		int result = 0;
		
		try {
			result = musicService.insertMusic(m, musicFile, 
					imageServerFolderPath, imgaeWebPath, mp3ServerFolderPath, mp3WebPath);
		} catch (Exception e) {
			log.error("error = {}", e.getMessage());
		}
		
		return result;		
	}
	
	
	
	/**
	 * @param musicNo
	 * @param cover
	 * @param music
	 * @param title
	 * @param artist
	 * @param lyrics
	 * @param genre
	 * @return
	 * 나중에 관리자 기능 할때 update 잘 되나 확인!!!
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value ="/updateMusic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int updateMusic(
			@RequestParam("musicNo") String musicNo,
			@RequestParam(value = "cover", required = false) MultipartFile cover,
			@RequestParam(value = "music",  required = false) MultipartFile music,
			@RequestParam("title") String title, 
			@RequestParam("artist") String artist,
			@RequestParam("lyrics") String lyrics,
			@RequestParam("genre") String genre
			){

		Music m = new Music();
		m.setMusicNo(musicNo);
		m.setMusicTitle(title);
		m.setMusicArtist(artist);
		m.setMusicLyrics(lyrics);
		m.setGenre(genre);
		
		
		String imgaeWebPath="/resources/image/";
		String mp3WebPath="/resources/mp3/";
		String imageServerFolderPath = application.getRealPath(imgaeWebPath);
		String mp3ServerFolderPath = application.getRealPath(mp3WebPath);
		
		// 해당 디렉토리가 존재하지 않는다면 생성
		File dir1 = new File(imageServerFolderPath);
		File dir2 = new File(mp3ServerFolderPath);
		if(!dir1.exists()) dir1.mkdirs();
		if(!dir2.exists()) dir2.mkdirs();
		
		MusicFile musicFile = new MusicFile();
		
		if(cover != null) {
			// 파일명 재정의 + 저장
			String changeName = Utils.saveFile(cover, imageServerFolderPath);
			musicFile.setCoverChangeName(changeName);
			musicFile.setCoverOriginName(cover.getOriginalFilename());
		};
		if(music != null) {
			String changeName = Utils.saveFile(music, mp3ServerFolderPath);
			musicFile.setMusicChangeName(changeName);
			musicFile.setMusicOriginName(music.getOriginalFilename());
		}
			
		int result = 0;
		
		try {
			result = musicService.updateMusic(m, musicFile,
					imageServerFolderPath, imgaeWebPath, mp3ServerFolderPath, mp3WebPath);
		} catch (Exception e) {
			log.error("error = {}", e.getMessage());
		}
		
		return result;		
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value ="/deleteMusic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int deleteMusic(@RequestParam("musicNo") String musicNo) {
		
		return musicService.deleteMusic(musicNo);
	}
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/insertPlaylist")
	public int insertPlaylist(
			@RequestParam("listName") String playlistName,
			@RequestParam("userNo") String userNo
			){		
		return musicService.insertPlaylist(playlistName, userNo);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/deletePlaylist")
	public int deletePlaylist(
			@RequestParam("playlistNo") String playlistNo
			){		
		return musicService.deletePlaylist(playlistNo);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/selectOneMusic")
	public Music selectOneMusic(
			@RequestParam("musicNo") String musicNo
			){
		return musicService.selectOneMusic(musicNo);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/addPlaylistSong")
	public int addPlaylistSong(
			@RequestParam("playlistNo") String playlistNo,			
			@RequestParam("musicNo") String musicNo
			){
		return musicService.addPlaylistSong(playlistNo, musicNo);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/removePlaylistSong")
	public int removePlaylistSong(
			@RequestParam("playlistNo") String playlistNo,			
			@RequestParam("musicNo") String musicNo
			){
		return musicService.removePlaylistSong(playlistNo, musicNo);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/increaseCount")
	public int increaseCount(
			@RequestParam(value = "musicNo", required = false) String musicNo
			){
		if(musicNo == null) {
			return 0;
		}
		return musicService.increaseCount(musicNo);
	}
	
	@Scheduled(cron = "0 0 * * * ?") // 매시 정각
	public void resetHourCount() {
		musicService.resetHourCount();
	}
	@Scheduled(cron = "0 0 0 * * ?") // 매일 자정
	public void resetDailyCount() {
		musicService.resetDailyCount();
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/searchMusic")
	public List<Music> searchMusic(
			@RequestParam("keyword") String keyword
			){
		return musicService.searchMusic(keyword);
	}
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/insertKeyword")
	public void insertKeyword(
			@RequestParam("keyword") String keyword
			){
		musicService.insertKeyword(keyword);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/selectRanking")
    public List<String> selectRanking(){
    	return musicService.selectRanking();
    }

}
