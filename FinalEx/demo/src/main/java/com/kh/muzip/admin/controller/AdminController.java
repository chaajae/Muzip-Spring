package com.kh.muzip.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.muzip.admin.model.vo.Pagination;
import com.kh.muzip.board.model.vo.Board;
import com.kh.muzip.admin.model.service.AdminService;
import com.kh.muzip.admin.model.vo.PageInfo;
import com.kh.muzip.member.model.vo.Member;
import com.kh.muzip.music.model.vo.Music;
import com.kh.muzip.setting.controller.SettingController;
import com.kh.muzip.setting.model.service.SettingService;
import com.kh.muzip.setting.model.vo.Contact;
import com.kh.muzip.setting.model.vo.Genre;
import com.kh.muzip.setting.model.vo.PaymentHistory;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectMemberListCount")
	public ResponseEntity<?> selectMemberListCount(@RequestBody HashMap<String, Object> m) {

		int listCount = adminService.selectMemberListCount();

		return ResponseEntity.ok().body(listCount);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectMemberList")
	public ResponseEntity<?> selectMemberList(@RequestBody HashMap<String, Object> m) {
	    
	    int listCount = adminService.selectMemberListCount();
	    int currentPage = m.get("currentPage") != null ? (int) m.get("currentPage") : 1;
	    String sortBy = m.get("sortBy") != null ? (String) m.get("sortBy") : "default"; 
	    String searchTerm = m.get("searchTerm") != null ? (String) m.get("searchTerm") : null;

	    int pageLimit = 10; // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
	    int boardLimit = 10; // 한 페이지에 보여질 게시글의 최대 갯수

	    PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
	    // 이건 정렬바꿀때 필요
	    ArrayList<Member> list = adminService.selectMemberList(pi, sortBy, searchTerm); // 검색어 추가

	    return ResponseEntity.ok().body(list);
	}




	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/updateMemberinfo")
	public ResponseEntity<?> updateMemberinfo(@RequestBody Member member) {

		int result = adminService.updateMemberinfo(member);

		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/WithdrawalMemberinfo")
	public ResponseEntity<?> WithdrawalMemberinfo(@RequestBody Member member) {

		int result = adminService.WithdrawalMemberinfo(member);

		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}

	}
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/RestoreMemberinfo")
	public ResponseEntity<?> RestoreMemberinfo(@RequestBody Member member) {
		
		int result = adminService.RestoreMemberinfo(member);
		
		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}
		
	}
	
	
	//-----------------------------회원관리 끝 게시글 관리 시작--------------------------------------
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectContentListCount")
	public ResponseEntity<?> selectContentListCount(@RequestBody HashMap<String, Object> m) {

		int listCount = adminService.selectContentListCount();

		return ResponseEntity.ok().body(listCount);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectContentList")
	public ResponseEntity<?> selectContentList(@RequestBody HashMap<String, Object> m) {
	    // 검색어, 검색 유형, 정렬 기준 추출
	    String searchTerm = m.get("searchTerm") != null ? (String) m.get("searchTerm") : "";
	    String searchType = m.get("searchType") != null ? (String) m.get("searchType") : "boardNo";

	    String sortBy = m.get("sortBy") != null ? (String) m.get("sortBy") : "default";

	    // 총 게시글 수는 검색 조건에 따라 달라질 수 있으므로 검색 조건을 파라미터로 넘김
	    int listCount = adminService.selectContentListCountByType(searchTerm, searchType);

	    int currentPage = m.get("currentPage") != null ? (int) m.get("currentPage") : 1;
	    int pageLimit = 10;
	    int boardLimit = 10;

	    PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

	    // 정렬과 검색 조건을 포함하여 게시글 목록을 가져옴
	    ArrayList<Board> list = adminService.selectContentList(pi, searchTerm, searchType, sortBy);
	    
	    
	    return ResponseEntity.ok().body(list);
	}

	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/adminDeleteContent")
	public ResponseEntity<?> adminDeleteContent(@RequestBody Board board) {

		int result = adminService.adminDeleteContent(board);

		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}

	}
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/adminRestoreContent")
	public ResponseEntity<?> adminRestoreContent(@RequestBody Board board) {
		
		int result = adminService.adminRestoreContent(board);
		
		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}
		
	}
	
	
	//-------------------------------------글 관리끝 음악관리 시작-------------------------------------------------
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectMusicListCount")
	public ResponseEntity<?> selectMusicListCount(@RequestBody HashMap<String, Object> m) {

		int listCount = adminService.selectMusicListCount();

		return ResponseEntity.ok().body(listCount);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectMusicList")
	public ResponseEntity<?> selectMusicList(@RequestBody HashMap<String, Object> m) {
		// 검색어, 검색 유형, 정렬 기준 추출
	    String searchTerm = m.get("searchTerm") != null ? (String) m.get("searchTerm") : "";
	    String searchType = m.get("searchType") != null ? (String) m.get("searchType") : "musicNo";

	    String sortBy = m.get("sortBy") != null ? (String) m.get("sortBy") : "default";

	    // 총 게시글 수는 검색 조건에 따라 달라질 수 있으므로 검색 조건을 파라미터로 넘김
	    int listCount = adminService.selectMusicListCountByType(searchTerm, searchType);
	    
		int currentPage = m.get("currentPage") != null ? (int) m.get("currentPage") : 1;
		int pageLimit = 10; // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit = 10; // 한 페이지에 보여질 게시글의 최대 갯수

		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		// 정렬과 검색 조건을 포함하여 게시글 목록을 가져옴
		ArrayList<Music> list = adminService.selectMusicList(pi, searchTerm, searchType, sortBy);

		System.out.println(list);
		return ResponseEntity.ok().body(list);
	}
	

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/adminDeleteMusic")
	public ResponseEntity<?> adminDeleteMusic(@RequestBody Music music) {

		int result = adminService.adminDeleteMusic(music);

		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}

	}
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/adminRestoreMusic")
	public ResponseEntity<?> adminRestoreMusic(@RequestBody Music music) {
		
		int result = adminService.adminRestoreMusic(music);
		
		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}
		
	}
	
	
	//=========================================================================================
	
	@Autowired
	private SettingService settingService;
	
	/* 문의목록갯수 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectContactListCount")
	public ResponseEntity<?> selectContactListCount(@RequestBody HashMap<String, Object> m){
		String userNo = null;
		String category = (String)m.get("category");
		String researchinput = (String)m.get("researchinput");
		
		HashMap<String, Object> map = new HashMap();
		map.put("userNo", userNo);
		map.put("category", category);
		map.put("researchinput", researchinput);
		
		int listCount = settingService.selectContactListCount(map);

		return ResponseEntity.ok().body(listCount);
		
		
	}
	
	
	/* 문의목록 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectContactList")
	public ResponseEntity<?> selectContactList(@RequestBody HashMap<String, Object> m){
		String userNo = null;
		String category = (String)m.get("category");
		String researchinput = (String)m.get("researchinput");
		
		HashMap<String, Object> map = new HashMap();
		map.put("userNo", userNo);
		map.put("category", category);
		map.put("researchinput", researchinput);
		
		int listCount = settingService.selectContactListCount(map);
		int currentPage = m.get("currentPage") != null ? (int) m.get("currentPage") : 1;
		int pageLimit = 10; // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit = 10; // 한 페이지에 보여질 게시글의 최대 갯수
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		map.put("pi", pi);
		ArrayList<Contact> list = settingService.selectContactList(map);
		
		return ResponseEntity.ok().body(list);
	}
	
	/* 문의답변업데이트 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/updateAdminReply")
	public ResponseEntity<?> updateAdminReply(@RequestBody HashMap<String, Object> m) {
		
		String contactNo = (String)m.get("contactNo");
		String adminReply = (String)m.get("adminReply");
		
		HashMap<String, Object> map = new HashMap();
		map.put("contactNo", contactNo);
		map.put("adminReply", adminReply);
		
		int result = adminService.updateAdminReply(map);

		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}

	}

	/* 문의삭제 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/DeleteContact")
	public ResponseEntity<?> DeleteContact(@RequestBody HashMap<String, Object> m) {

		String contactNo = (String)m.get("contactNo");
		
		HashMap<String, Object> map = new HashMap();
		map.put("contactNo", contactNo);
		
		int result = adminService.DeleteContact(map);

		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}

	}
	
	/* 문의복구 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/RestoreContact")
	public ResponseEntity<?> RestoreContact(@RequestBody HashMap<String, Object> m) {
		
		String contactNo = (String)m.get("contactNo");
		
		HashMap<String, Object> map = new HashMap();
		map.put("contactNo", contactNo);
		
		int result = adminService.RestoreContact(map);
		
		if (result > 0) {
			return ResponseEntity.ok().body(Map.of("message", "정보가 수정되었습니다."));
		} else {
			return ResponseEntity.badRequest().body(Map.of("message", "정보 수정에 실패했습니다."));
		}
		
	}
	
	
	/* 결제목록갯수 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectPaymentListCount")
	public ResponseEntity<?> selectPaymentListCount(@RequestBody HashMap<String, Object> m){
		String category = (String)m.get("category");
		String researchinput = (String)m.get("researchinput");
		
		HashMap<String, Object> map = new HashMap();
		map.put("category", category);
		map.put("researchinput", researchinput);
		
		int listCount = adminService.selectPaymentListCount(map);

		return ResponseEntity.ok().body(listCount);
		
		
	}
	
	
	/* 결제목록 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/selectPaymentList")
	public ResponseEntity<?> selectPaymentList(@RequestBody HashMap<String, Object> m){
		String category = (String)m.get("category");
		String researchinput = (String)m.get("researchinput");
		
		HashMap<String, Object> map = new HashMap();
		map.put("category", category);
		map.put("researchinput", researchinput);
		
		int listCount = adminService.selectPaymentListCount(map);
		int currentPage = m.get("currentPage") != null ? (int) m.get("currentPage") : 1;
		int pageLimit = 10; // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit = 10; // 한 페이지에 보여질 게시글의 최대 갯수
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		map.put("pi", pi);
		ArrayList<PaymentHistory> list = adminService.selectPaymentList(map);
		
		return ResponseEntity.ok().body(list);
	}
	
	
	
	

}
