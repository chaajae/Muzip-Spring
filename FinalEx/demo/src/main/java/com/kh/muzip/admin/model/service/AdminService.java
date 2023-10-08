package com.kh.muzip.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.muzip.admin.model.vo.PageInfo;
import com.kh.muzip.board.model.vo.Board;
import com.kh.muzip.member.model.vo.Member;
import com.kh.muzip.music.model.vo.Music;
import com.kh.muzip.setting.model.vo.Contact;
import com.kh.muzip.setting.model.vo.PaymentHistory;

public interface AdminService {

	int selectMemberListCount();

	ArrayList<Member> selectMemberList(PageInfo pi);

	int updateMemberinfo(Member member);

	int WithdrawalMemberinfo(Member member);

	int RestoreMemberinfo(Member member);

	int selectContentListCount();

	ArrayList<Board> selectContentList(PageInfo pi,String searchTerm,String searchType,String sortBy);
	int selectContentListCountByType(String searchTerm, String searchType);
	
	int adminDeleteContent(Board board);

	int adminRestoreContent(Board board);

	int selectMusicListCount();

	ArrayList<Music> selectMusicList(PageInfo pi,String searchTerm,String searchType,String sortBy);
	int selectMusicListCountByType(String searchTerm, String searchType);

	int adminDeleteMusic(Music music);

	int adminRestoreMusic(Music music);

	ArrayList<Member> selectMemberList(PageInfo pi, String sortBy,String searchTerm);

	int updateAdminReply(HashMap<String, Object> map);

	int DeleteContact(HashMap<String, Object> map);

	int RestoreContact(HashMap<String, Object> map);

	int selectPaymentListCount(HashMap<String, Object> map);

	ArrayList<PaymentHistory> selectPaymentList(HashMap<String, Object> map);

	

	

	

}
