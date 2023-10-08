package com.kh.muzip.member.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.member.model.vo.Member;

@Repository
public class MyProfileDao {
	@Autowired
    private SqlSessionTemplate session;

	@Transactional
	public boolean saveProfileImg(Attachment pat) {
	    int result = session.insert("myprofile.insertProfileImg", pat);
	    return result > 0;
	}

	@Transactional
	public boolean saveBackImg(Attachment bat) {
	    int result = session.insert("myprofile.insertBackImg", bat);
	    return result > 0;
	}


	public int saveMemberInfo(Member memberData) {
		return session.update("myprofile.saveMemberInfo", memberData);
	}

	
	public int deactivateImage(String userNo, int fileLevel) {
	    Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("userNo", userNo);
	    paramMap.put("fileLevel", fileLevel);
	    return session.update("myprofile.deactivateImage", paramMap);
	}

	public Member getMemberByUserNo(int userNoInt) {
		 return session.selectOne("myprofile.getMemberByUserNo", userNoInt);
	}


	public List<Attachment> getAttachmentsByUserNo(String userNo) {
		return session.selectList("myprofile.getAttachmentsByUserNo", userNo);
	}

	public Member getUserData(int userNoInt) {
		
		return session.selectOne("myprofile.getUserData",userNoInt);
	}
	
	
}
