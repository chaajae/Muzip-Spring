package com.kh.muzip.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.member.model.dao.MyProfileDao;
import com.kh.muzip.member.model.vo.Member;

@Service
public class MyProfileServiceImpl implements MyProfileService{

	
	@Autowired
	private MyProfileDao myprofileDao;

	@Override
    public boolean saveProfileImg(Attachment pat) {
        return myprofileDao.saveProfileImg(pat);
    }

	@Override
	public int saveMemberInfo(Member memberData) {
		return myprofileDao.saveMemberInfo(memberData);
		
	}

	@Override
	public boolean saveBackImg(Attachment bat) {
		 return myprofileDao.saveBackImg(bat);
	}
	
	public void deactivateImage(String userNo, int fileLevel) {
		myprofileDao.deactivateImage(userNo, fileLevel);
	}

	@Override
	public Member getMemberByUserNo(int userNoInt) {
		return myprofileDao.getMemberByUserNo(userNoInt);
	}

	@Override
	public List<Attachment> getAttachmentsByUserNo(String userNo) {
		return myprofileDao.getAttachmentsByUserNo(userNo);
	}

	@Override
	public Member getUserData(int userNoInt) {
		return myprofileDao.getUserData(userNoInt);
	}
	
	
}
