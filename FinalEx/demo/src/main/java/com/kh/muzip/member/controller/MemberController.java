package com.kh.muzip.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.muzip.member.model.service.MemberService;
import com.kh.muzip.member.model.service.MyProfileService;
import com.kh.muzip.member.model.vo.Follow;
import com.kh.muzip.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class MemberController {
	
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MyProfileService myprofileService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/enrollM")
    public ResponseEntity<String> insertMember(@RequestBody Member member) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getUserPwd());

        member.setUserPwd(encodedPassword);
        int result = memberService.insertMember(member);

        if (result > 0) {
            return ResponseEntity.ok("회원가입에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입에 실패하였습니다."); 
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/loginM")
    public ResponseEntity<Member> loginMember(@RequestBody Member member) {
    	Member loginUser = memberService.loginMember(member.getUserId());
    	
    	if (loginUser != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean passwordMatches = encoder.matches(member.getUserPwd(), loginUser.getUserPwd());
            
            if (passwordMatches) {
                return ResponseEntity.ok(loginUser); // 로그인 성공
            } 
        } 
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/friendSearch")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam("query") String query) {
        List<Member> members = memberService.searchMembers(query);
        return ResponseEntity.ok(members);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/checkId")
    public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam("userId") String userId) {
        Boolean usercheckId = memberService.checkId(userId);
        Map<String, Boolean> response = new HashMap<>();
        
        response.put("IsCheck", usercheckId);
        return ResponseEntity.ok(response);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/checkName")
    public ResponseEntity<Map<String, Boolean>> checkName(@RequestParam("userName") String userName) {
        Boolean usercheckName = memberService.checkName(userName);
        Map<String, Boolean> response = new HashMap<>();
        
        response.put("IsNameCheck", usercheckName);
        return ResponseEntity.ok(response);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/checkFollow")
    public ResponseEntity<Map<String, Boolean>> checkFollow(@RequestParam("userId") String userId, @RequestParam("memberId") String memberId) {
    	boolean isFriendValue = memberService.checkFollow(userId, memberId); // 친구 여부 확인 함수 호출

        Map<String, Boolean> response = new HashMap<>();
        response.put("isFriend", isFriendValue);

        return ResponseEntity.ok(response);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Follow")
    public ResponseEntity<?> addFollow(@RequestParam("userId") String userId, @RequestParam("memberId") String memberId) {
        int result = memberService.addFollow(userId, memberId);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "친구가 추가되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "친구 추가에 실패했습니다."));
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/unFollow")
    public ResponseEntity<?> unFollow(@RequestParam("userId") String userId, @RequestParam("memberId") String memberId) {
    	int success = memberService.unFollow(userId, memberId);
        if (success>0) {
            return ResponseEntity.ok().body(Map.of("message", "친구가 해제되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "친구 해제에 실패했습니다."));
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/userInfo")
    public ResponseEntity<String> addFollow(@RequestParam("userNo") String userNo){
    	Member userData = myprofileService.getUserData(Integer.parseInt(userNo));
    	String userInfo = userData.getUserInfo();
        return ResponseEntity.ok(userInfo);
    }
}




