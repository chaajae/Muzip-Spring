
// =============
// Create by 차재현
// =============


package com.kh.muzip.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.board.model.vo.Board;
import com.kh.muzip.board.model.vo.BoardExt;
import com.kh.muzip.board.model.vo.Reply;

public interface BoardService {

	public int insertBoard(Board b, List<Attachment> atList, String serverFolderPath, String webPath) throws Exception;

	public List<BoardExt> selectBoardList();

	public String boomUp(Map<String, String> boomUpData);

	public Reply insertReply(Reply r);

	public List<Map<String, String>> getUserIdList();

	public List<Map<String, String>> getUserProfileImgList();

	public List<Map<String, String>> getAllMusicList();

	public int updateBoard(Board b, List<MultipartFile> files, String serverFolderPath, String webPath,
			List<Integer> deleteList, List<Integer> updateList);

	public List<BoardExt> getMyBoard(String userNo);

	public int deleteBoard(String boardNo);

	public int deleteReply(Reply r);
	
}
