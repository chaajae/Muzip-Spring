package com.kh.muzip.member.model.service;

import java.util.List;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.member.model.vo.Member;

public interface MyProfileService {

	boolean saveProfileImg(Attachment pat);

	int saveMemberInfo(Member memberData);

	boolean saveBackImg(Attachment bat);

	void deactivateImage(String userNo, int fileLevel);

	Member getMemberByUserNo(int userNoInt);


	List<Attachment> getAttachmentsByUserNo(String userNo);
 
	Member getUserData(int userNoInt);

}
