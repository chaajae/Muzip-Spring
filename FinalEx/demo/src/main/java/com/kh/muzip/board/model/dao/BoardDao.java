

// ==============
// Create by 차재현
// ==============

package com.kh.muzip.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.board.model.vo.Board;
import com.kh.muzip.board.model.vo.BoardExt;
import com.kh.muzip.board.model.vo.Reply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;

	public int insertBoard(Board b) {
		int result = 0;
		result = sqlSession.insert("board.insertBoard",b);
												
		if(result > 0) {
			result = Integer.parseInt(b.getBoardNo());
		}
		return result;
	}

	public int insertAttachmentList(List<Attachment> atList) {
		return sqlSession.insert("board.insertAttachmentList",atList);
	}

	public List<BoardExt> selectBoardList() {
		return sqlSession.selectList("board.selectBoardList");
	}

	public Object selectBoomUp(Map<String, String> boomUpData) {
		return sqlSession.selectOne("board.selectBoomUp",boomUpData); 
	}

	public int deleteBoomUp(Map<String, String> boomUpData) {
		return sqlSession.delete("board.deleteBoomUp",boomUpData);
	}

	public int insertBoomUp(Map<String, String> boomUpData) {
		return sqlSession.insert("board.insertBoomUp",boomUpData);
	}

	public int insertReply(Reply r) {
		int result = 0;
		result = sqlSession.insert("board.insertReply",r);
		if(result > 0) {
			result = Integer.parseInt(r.getReplyNo());
		}
		return result;
	}

	public Reply selectReplyOne(int replyNo) {
		return sqlSession.selectOne("board.selectReplyOne",replyNo);
	}

	public List<Map<String, String>> getUserIdList() {
		return sqlSession.selectList("board.getUserIdList");
	}

	public List<Map<String, String>> getUserProfileImgList() {
		return sqlSession.selectList("board.getUserProfileImgList");
	}

	public List<Map<String, String>> getAllMusicList() {
		return sqlSession.selectList("board.getAllMusicList");
	}

	public int updateBoard(Board b) {
		return sqlSession.update("board.updateBoard",b);
	}

	public int deleteAttachment(Map<String, Object> map) {
		return sqlSession.delete("board.deleteAttachment",map);
	}

	public int insertAttachment(Attachment at) {
		return sqlSession.insert("board.insertAttachment",at);
	}

	public List<BoardExt> getMyBoard(String userNo) {
		return sqlSession.selectList("board.getMyBoard",userNo);
	}

	public int deleteBoard(String boardNo) {
		int result = sqlSession.delete("board.deleteBoard",boardNo);
		if(result > 0) {
			sqlSession.delete("board.deleteBoardAt",boardNo);
			sqlSession.delete("board.deleteBoardLB",boardNo);
			sqlSession.delete("board.deleteBoardR",boardNo);
		}
		return result;
		
	}
	public int deleteReply(Reply r) {
		return sqlSession.delete("board.deleteReply",r);
	}
	

}
