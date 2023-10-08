

// ==============
// Create by 차재현
// ==============


package com.kh.muzip.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.muzip.board.model.dao.BoardDao;
import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.board.model.vo.Board;
import com.kh.muzip.board.model.vo.BoardExt;
import com.kh.muzip.board.model.vo.Reply;
import com.kh.muzip.common.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDao boardDao;
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public int insertBoard(Board b, List<Attachment> atList, String serverFolderPath, String webPath) throws Exception {
		b.setBoardContent(Utils.XSSHandling(b.getBoardContent()));
		b.setBoardContent(Utils.newLineHandling(b.getBoardContent()));
		
		int boardNo = boardDao.insertBoard(b);
		int result = 0;
		if(boardNo > 0 && !atList.isEmpty()) {
			for(Attachment at  : atList) {
				at.setBoardNo(boardNo+"");
				at.setFilePath(webPath);
			}
			result = boardDao.insertAttachmentList(atList);
			if(result != atList.size()) {
				throw new Exception("예외발생");
			}
		}else {
			result = boardNo;
		}
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public List<BoardExt> selectBoardList() {
		List<BoardExt> boardList = boardDao.selectBoardList();
		for(BoardExt b : boardList) {
			b.setBoardContent(Utils.newLineClear(b.getBoardContent()));
		}
		return boardList;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public List<Map<String, String>> getUserIdList() {
		List<Map<String, String>> idList = boardDao.getUserIdList();
		return idList;
	}
	
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public List<Map<String, String>> getUserProfileImgList() {
		List<Map<String, String>> imgList = boardDao.getUserProfileImgList();
		return imgList;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public List<Map<String, String>> getAllMusicList() {
		List<Map<String, String>> allMusicList = boardDao.getAllMusicList();
		return allMusicList;
	}
	
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public String boomUp(Map<String, String> boomUpData) {
		Object selectResult = null;
		selectResult = boardDao.selectBoomUp(boomUpData);
		
		String resultValue = "";
		int result = 0;
		if(selectResult != null) {
			result = boardDao.deleteBoomUp(boomUpData);
			if(result > 0) {
				resultValue = "삭제";
			}
		}else {
			result = boardDao.insertBoomUp(boomUpData);
			if(result > 0) {
				resultValue = "삽입";
			}
		}
		return resultValue;
	}
	
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public Reply insertReply(Reply r) {
		int replyNo = 0;
		Reply returnR = new Reply();
		replyNo = boardDao.insertReply(r);
		if(replyNo > 0 ) {
			returnR = boardDao.selectReplyOne(replyNo);
		}
		return returnR;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public int updateBoard(Board b, List<MultipartFile> files, String serverFolderPath, String webPath,
			List<Integer> deleteList, List<Integer> updateList) {
		b.setBoardContent(Utils.XSSHandling(b.getBoardContent()));
		b.setBoardContent(Utils.newLineHandling(b.getBoardContent()));
		
		int result = boardDao.updateBoard(b);
		
		if(result > 0) {
		
			List<Attachment> attachList = new ArrayList();
			if(files != null) {
				for(int i = 0; i < files.size(); i++) {
					if(!files.get(i).isEmpty()) {
						String changeName = Utils.saveFile(files.get(i), serverFolderPath);
						Attachment at = new Attachment();
						at.setUserNo(b.getUserNo());
						at.setBoardNo(b.getBoardNo());
						at.setOriginName(files.get(i).getOriginalFilename());
						at.setFilePath(webPath);
						at.setChangeName(changeName);
						at.setFileLevel(updateList.get(i));				
						attachList.add(at);
					}
				}
			}
			
			if(deleteList.size() != 0) {
				Map<String,Object> map = new HashMap();
				String deleteListAsString = deleteList.stream()
					    .map(Object::toString)
					    .collect(Collectors.joining(","));
				map.put("boardNo", b.getBoardNo());
				map.put("deleteList", deleteListAsString);
				result = boardDao.deleteAttachment(map);
			}
			if(result > 0 ) {
				for( Attachment at : attachList) {
						result = boardDao.insertAttachment(at);
				}
			}
		}
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public List<BoardExt> getMyBoard(String userNo) {
		List<BoardExt> myBoardList = boardDao.getMyBoard(userNo);
		for(BoardExt b : myBoardList) {
			b.setBoardContent(Utils.newLineClear(b.getBoardContent()));
		}
		return myBoardList;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public int deleteBoard(String boardNo) {
		return boardDao.deleteBoard(boardNo);
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public int deleteReply(Reply r) {
		return boardDao.deleteReply(r);
	}
	
}
