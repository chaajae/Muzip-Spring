package com.kh.muzip.music.model.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.muzip.music.model.dao.MusicDao;
import com.kh.muzip.music.model.vo.Music;
import com.kh.muzip.music.model.vo.MusicFile;
import com.kh.muzip.music.model.vo.Playlist;
import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.common.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MusicServiceImpl implements MusicService{
	
	@Autowired
	private MusicDao musicDao;

	@Override
	public ArrayList<Playlist> selectPlaylist(int userNo) {
		return musicDao.selectPlaylist(userNo);
	}
	
	@Override
	public List<String> getGenre(String userNo){
		return musicDao.getGenre(userNo);
	}
	@Override
	public ArrayList<ArrayList<Music>> selectRecommendList(List<String> genreList) {
		return musicDao.selectRecommendList(genreList);
	}

	@Override
	public int insertMusic(Music m, MusicFile musicFile, String imageServerFolderPath, String imageWebPath,
			String mp3ServerFolderPath, String mp3WebPath) throws Exception {

		//    크로스사이트스크립트공격(의도적으로 쿼리문을 입력해 공격)을 방지하기위한 메소드 추가
		//	  + 텍스트에리어태그에 엔터, 스페이스바를 개행문자로 변환처리
		m.setMusicTitle(Utils.XSSHandling(m.getMusicTitle()));
		m.setMusicArtist(Utils.XSSHandling(m.getMusicArtist()));
		m.setMusicLyrics(Utils.XSSHandling(m.getMusicLyrics()));
		m.setMusicLyrics(Utils.newLineHandling(m.getMusicLyrics()));
		
		String musicNo = musicDao.insertMusic(m); // 반환값은 처리된 행의 갯수가 아닌 pk값을 반환받음
											   // 제대로 삽입이 안될 경우 0을 반환할 예정
		
		int result = 0;
		if(musicNo != "" && musicNo != null && musicFile != null) {
			musicFile.setMusicNo(musicNo);
			musicFile.setCoverPath(imageWebPath + musicFile.getCoverChangeName());
			musicFile.setMusicPath(mp3WebPath + musicFile.getMusicChangeName());
			result = musicDao.insertMusicFile(musicFile);
			
			if(result == 0) {
				throw new Exception("예외발생");
			}
		}
		
		return result;
	}

	@Override
	public int updateMusic(Music m, MusicFile musicFile, String imageServerFolderPath, String imageWebPath,
			String mp3ServerFolderPath, String mp3WebPath) throws Exception {
			//	    크로스사이트스크립트공격(의도적으로 쿼리문을 입력해 공격)을 방지하기위한 메소드 추가
			//	  + 텍스트에리어태그에 엔터, 스페이스바를 개행문자로 변환처리
			m.setMusicTitle(Utils.XSSHandling(m.getMusicTitle()));
			m.setMusicArtist(Utils.XSSHandling(m.getMusicArtist()));
			m.setMusicLyrics(Utils.XSSHandling(m.getMusicLyrics()));
			m.setMusicLyrics(Utils.newLineHandling(m.getMusicLyrics()));
			
			int result = musicDao.updateMusic(m);
			int result2 = 0;
			
			if(result > 0) {
				// 기존의 musicFile의 정보를 받아오고
				MusicFile originMusicFile = musicDao.selectOneMusicFile(m.getMusicNo());
				
				// 수정시킨 음악파일, 커버 파일이 존재하면 바꾼값으로 새로 setting
				// 없으면 기존 originMusicFile 값으로 setting
				musicFile.setMusicNo(m.getMusicNo());
				if(musicFile.getCoverChangeName() == null || musicFile.getCoverChangeName() == "") {
					musicFile.setCoverOriginName(originMusicFile.getCoverOriginName());
					musicFile.setCoverChangeName(originMusicFile.getCoverChangeName());
					musicFile.setCoverPath(originMusicFile.getCoverPath());
				}else {
					musicFile.setCoverPath(imageWebPath + musicFile.getCoverChangeName());
				}
				
				if(musicFile.getMusicChangeName() == null || musicFile.getMusicChangeName() == "") {
					musicFile.setMusicOriginName(originMusicFile.getMusicOriginName());
					musicFile.setMusicChangeName(originMusicFile.getMusicChangeName());
					musicFile.setMusicPath(originMusicFile.getMusicPath());
				}else {
					musicFile.setMusicPath(mp3WebPath + musicFile.getMusicChangeName());					
				}
				result2 = musicDao.updateMusicFile(musicFile);
			}else {
				throw new Exception("예외발생");
			}
			
			return result2;
	}
	
	@Override
	public int deleteMusic(String musicNo) {
		return musicDao.deleteMusic(musicNo);
	}

	@Override
	public int insertPlaylist(String playlistName, String userNo) {
		return musicDao.insertPlaylist(playlistName, userNo);
	}

	@Override
	public int deletePlaylist(String playlistNo) {
		return musicDao.deletePlaylist(playlistNo);
	}

	@Override
	public Music selectOneMusic(String musicNo) {
		return musicDao.selectOneMusic(musicNo);
	}

	@Override
	public int addPlaylistSong(String playlistNo, String musicNo) {
		return musicDao.addPlaylistSong(playlistNo, musicNo);
	}

	@Override
	public int removePlaylistSong(String playlistNo, String musicNo) {
		return musicDao.removePlaylistSong(playlistNo, musicNo);
	}

	@Override
	public int increaseCount(String musicNo) {
		return musicDao.increaseCount(musicNo);
	}
	@Override
	public void resetHourCount() {
		musicDao.resetHourCount();
	}
	@Override
	public void resetDailyCount() {
		musicDao.resetDailyCount();
	}
	
	@Override
	public void insertKeyword(String keyword) {
		musicDao.insertKeyword(keyword);
	}

	@Override
	public List<Music> searchMusic(String keyword) {
		return musicDao.searchMusic(keyword);
	}

	@Override
    public List<String> selectRanking() {
       return musicDao.selectRanking();
    }


}
