package com.kh.muzip.admin.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.muzip.admin.model.vo.PageInfo;
import com.kh.muzip.board.model.vo.Board;
import com.kh.muzip.member.model.vo.Member;
import com.kh.muzip.music.model.vo.Music;


import com.kh.muzip.setting.model.vo.Contact;
import com.kh.muzip.setting.model.vo.Genre;
import com.kh.muzip.setting.model.vo.PaymentHistory;

@Repository
public class AdminDao {
	
	@Autowired
	private SqlSessionTemplate session;
	
	

	public int selectMemberListCount() {
		
		return session.selectOne("admin.selectMemberListCount");
	}
	
	



	public ArrayList<Member> selectMemberList(PageInfo pi) {
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage() -1) * 10;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)session.selectList("admin.selectMemberList", null, rowBounds);
	}





	public int updateMemberinfo(Member member) {
		return session.update("admin.updateMemberinfo",member);
	}



	public int WithdrawalMemberinfo(Member member) {
		return session.update("admin.WithdrawalMemberinfo",member);
	}



	public int RestoreMemberinfo(Member member) {
		return session.update("admin.RestoreMemberinfo",member);
	}


	//회원관리


	public int selectContentListCount() {
		return session.selectOne("admin.selectContentListCount");
	}





	public ArrayList<Board> selectContentList(PageInfo pi, String searchTerm, String searchType, String sortBy) {
	    int limit = pi.getBoardLimit();
	    int offset = (pi.getCurrentPage() - 1) * 10;
	    Map<String, Object> params = new HashMap<>();
	    params.put("searchTerm", searchTerm);
	    params.put("searchType", searchType);
	    params.put("sortBy", sortBy);
	    
	    RowBounds rowBounds = new RowBounds(offset, limit);
	    
	    return (ArrayList)session.selectList("admin.selectContentList", params, rowBounds);
	}


	public int selectContentListCountByType(String searchTerm, String searchType) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("searchTerm", searchTerm);
	    params.put("searchType", searchType);

	    return session.selectOne("admin.selectContentListCountByType", params);
	}



	public int adminDeleteContent(Board board) {
		return session.update("admin.adminDeleteContent",board);
	}





	public int adminRestoreContent(Board board) {
		return session.update("admin.adminRestoreContent",board);
	}


	// 글 관리


	public int selectMusicListCount() {
		return session.selectOne("admin.selectMusicListCount");
	}





	public ArrayList<Music> selectMusicList(PageInfo pi,String searchTerm,String searchType,String sortBy) {
		int limit = pi.getBoardLimit();
	    int offset = (pi.getCurrentPage() - 1) * 10;
	    Map<String, Object> params = new HashMap<>();
	    params.put("searchTerm", searchTerm);
	    params.put("searchType", searchType);
	    params.put("sortBy", sortBy);
	    
	    RowBounds rowBounds = new RowBounds(offset, limit);
	    
	    return (ArrayList)session.selectList("admin.selectMusicList", params, rowBounds);
	}

	public int selectMusicListCountByType(String searchTerm, String searchType) {
		Map<String, Object> params = new HashMap<>();
	    params.put("searchTerm", searchTerm);
	    params.put("searchType", searchType);

	    return session.selectOne("admin.selectMusicListCountByType", params);
	}



	public int adminDeleteMusic(Music music) {
		return session.update("admin.adminDeleteMusic",music);
	}





	public int adminRestoreMusic(Music music) {
		return session.update("admin.adminRestoreMusic",music);
	}





	public ArrayList<Member> selectMemberList(PageInfo pi, String sortBy, String searchTerm) {
	    int limit = pi.getBoardLimit();
	    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();

	    HashMap<String, Object> map = new HashMap<>();
	    map.put("sortBy", sortBy);
	    map.put("searchTerm", searchTerm); // searchTerm 추가

	    RowBounds rowBounds = new RowBounds(offset, limit);

	    return (ArrayList) session.selectList("admin.selectMemberListBySort", map, rowBounds);
	}






	

	public int updateAdminReply(HashMap<String, Object> map) {
		return session.update("admin.updateAdminReply",map);
	}







	

	public int DeleteContact(HashMap<String, Object> map) {
		return session.update("admin.DeleteContact",map);
	}








	public int RestoreContact(HashMap<String, Object> map) {
		return session.update("admin.RestoreContact",map);
	}





	public int selectPaymentListCount(HashMap<String, Object> map) {
		return session.selectOne("admin.selectPaymentListCount",map);
	}





	public ArrayList<PaymentHistory> selectPaymentList(HashMap<String, Object> map) {
		int limit = ((PageInfo)map.get("pi")).getBoardLimit();
		int offset = (((PageInfo)map.get("pi")).getCurrentPage() -1) * 10;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)session.selectList("admin.selectPaymentList", map, rowBounds);
	}







	



}
