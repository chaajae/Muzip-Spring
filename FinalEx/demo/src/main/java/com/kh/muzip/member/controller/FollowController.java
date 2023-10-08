package com.kh.muzip.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.muzip.member.model.service.FollowService;
import com.kh.muzip.member.model.vo.Follow;

@RestController
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class FollowController {

    @Autowired
    private FollowService followService;
    
    @CrossOrigin(origins = "http://localhost:3000")
        @GetMapping("/followlist")
        public List<Follow> getFollowsForUser(@RequestParam("userId") String userId) {
            // userId를 기반으로 해당 유저의 팔로우 데이터 가져오기
            return followService.getFollowsByUserId(userId);
        }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/followerlist")
    public List<Follow> getFollowerList(@RequestParam String userId) {
        return followService.getFollowerList(userId);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    // 사용자가 팔로워들을 팔로우하고있는지 체크
    @PostMapping("/checkMultipleFollow")
    public Map<String, Boolean> checkMultipleFollows(@RequestBody Map<String, Object> request) {
        String userId = (String) request.get("userId");
        List<String> followerIds = (List<String>) request.get("followerIds");
        return followService.checkMultipleFollows(userId, followerIds);
    }

    
    
    
}

