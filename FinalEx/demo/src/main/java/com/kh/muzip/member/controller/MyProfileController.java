package com.kh.muzip.member.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.muzip.board.model.vo.Attachment;
import com.kh.muzip.common.Utils;
import com.kh.muzip.member.model.service.MyProfileService;
import com.kh.muzip.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class MyProfileController {

    @Autowired
    private MyProfileService myprofileService;

    @Autowired
    private ServletContext application;
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/uploadMyProfileImg")
    public ResponseEntity<?> uploadMyProfileImg(
            @RequestParam("userNo") String userNo,
            @RequestParam(value="profileImage", required=false) MultipartFile profileImage,
            @RequestParam(value="backgroundImage", required=false) MultipartFile backgroundImage) {
        
        // 이미지를 저장할 경로 설정
        String savePath = application.getRealPath("/resources/image/");
        File dir = new File(savePath);
        
        // 해당 경로에 폴더가 없으면 폴더를 생성
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        boolean profileSaved = false;  // 프로필 이미지 저장 성공 여부를 판단하는 변수
        boolean bgSaved = false;       // 배경 이미지 저장 성공 여부를 판단하는 변수
        
        String profileImageUrl = null; // 프로필 이미지 URL
        String backgroundImageUrl = null; // 배경 이미지 URL

        // 프로필 이미지 저장
        if (profileImage != null && !profileImage.isEmpty()) {
            myprofileService.deactivateImage(userNo, 1);

            String profileChangeName = Utils.saveFile(profileImage, savePath);
            profileImageUrl = "/resources/image/" + profileChangeName; // 프로필 이미지 URL 생성

            Attachment pat = new Attachment();
            pat.setUserNo(userNo);
            pat.setBoardNo("999");
            pat.setOriginName(profileImage.getOriginalFilename());
            pat.setChangeName(profileChangeName);
            pat.setFilePath("/resources/image/");
            pat.setFileLevel(1);
            pat.setStatus("Y");
            
            try {
                profileSaved = myprofileService.saveProfileImg(pat); // 프로필 이미지 DB 저장
            } catch (Exception e) {
                log.error("에러 = {}", e.getMessage());
            }
        } else {
            profileSaved = true;
        }

        // 배경 이미지 저장
        if (backgroundImage != null && !backgroundImage.isEmpty()) {
            myprofileService.deactivateImage(userNo, 2);

            String bgChangeName = Utils.saveFile(backgroundImage, savePath);
            backgroundImageUrl = "/resources/image/" + bgChangeName; // 배경 이미지 URL 생성

            Attachment bat = new Attachment();
            bat.setUserNo(userNo);
            bat.setBoardNo("999");
            bat.setOriginName(backgroundImage.getOriginalFilename());
            bat.setChangeName(bgChangeName);
            bat.setFilePath("/resources/image/");
            bat.setFileLevel(2);
            bat.setStatus("Y");
            
            try {
                bgSaved = myprofileService.saveBackImg(bat); // 배경 이미지 DB 저장
            } catch (Exception e) {
                log.error("에러 = {}", e.getMessage());
            }
        } else {
            bgSaved = true;
        }

        // 이미지 저장 성공 여부에 따라 응답 반환
        if (profileSaved && bgSaved) {
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "프로필이미지 등록 성공하였습니다.");
            if (profileImageUrl != null) responseMap.put("profileImageURL", profileImageUrl);
            if (backgroundImageUrl != null) responseMap.put("backgroundImageURL", backgroundImageUrl);
            
            return ResponseEntity.ok(responseMap); // 성공 응답 반환
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필이미지 등록 실패하였습니다."); // 실패 응답 반환
        }
    }




    @PostMapping("/uploadMyProfileOther")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> uploadProfileOther(@RequestBody Member memberData) {
        
        // userInfo나 backMuNo가 null일 경우의 처리
        if (memberData.getUserInfo() == null) {
            // null인 경우 원하는 대로 처리
            memberData.setUserInfo("0");
        }

        if (memberData.getBackMuNo() == null) {
            // null인 경우 원하는 대로 처리
            memberData.setBackMuNo("0");
        }

        // 데이터베이스에 memberData 정보 저장하고 그 결과를 받아옴
        int result = 0;
        
        try {
            result = myprofileService.saveMemberInfo(memberData);
        } catch (Exception e) {
            // 오류 로깅
            log.error("error = {}", e.getMessage());
        }

        // 데이터베이스 저장 결과에 따라 응답을 반환
        if(result > 0) {
            return ResponseEntity.ok("멤버 정보가 성공적으로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("멤버 정보 업데이트에 실패하였습니다.");
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getUserProfile/{userNo}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userNo) {
        try {
        	
        	 // userNo를 int로 변환
            int userNoInt = Integer.parseInt(userNo);

            // Service를 통해 userProfile 데이터 가져오기
            Member memberData = myprofileService.getMemberByUserNo(userNoInt);

         // Service를 통해 Attachment 데이터 가져오기
            List<Attachment> attachmentDataList = myprofileService.getAttachmentsByUserNo(userNo);

            Map<String, Object> responseMap = new HashMap<>();

            if (memberData != null) {
                responseMap.put("userInfo", memberData.getUserInfo());
                responseMap.put("backMuNo", memberData.getBackMuNo());
            }

            if (attachmentDataList != null && !attachmentDataList.isEmpty()) {
                for (Attachment attachmentData : attachmentDataList) {
                    if (attachmentData.getFileLevel() == 1) {
                        responseMap.put("profileImageURL", attachmentData.getFilePath() + attachmentData.getChangeName());
                    } else if (attachmentData.getFileLevel() == 2) {
                        responseMap.put("backgroundImageURL", attachmentData.getFilePath() + attachmentData.getChangeName());
                    }
                }
            }

            return ResponseEntity.ok(responseMap);

        } catch (NumberFormatException e) {
            log.error("userNo 파싱 오류:", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        } catch (Exception e) {
            log.error("프로필 정보 가져오기 실패:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 정보 가져오기 실패");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getUserData/{userNo}")
    public ResponseEntity<?> getUserData(@PathVariable("userNo") String userNo) {
    	if (userNo == null || "undefined".equals(userNo)) {
    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid userNo value");
    	}

        try {
            // userNo를 int로 변환
            int userNoInt = Integer.parseInt(userNo);
            Member userData = myprofileService.getUserData(userNoInt);
            
            if (userData != null) {
                return ResponseEntity.ok(userData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자 정보가 없습니다.");
            }
        } catch (Exception e) {
            log.error("사용자 정보 가져오기 에러", e);
            // 여기서 에러의 세부 내용을 클라이언트에게 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러: " + e.getMessage());
        }
    }

    

}

