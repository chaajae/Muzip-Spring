package com.kh.muzip.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.muzip.chat.service.ChatService;
import com.kh.muzip.chat.vo.ChatMessage;
import com.kh.muzip.chat.vo.ChatRoom;
import com.kh.muzip.chat.vo.ChatRoomJoin;
import com.kh.muzip.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@RequestMapping("/messages")
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class ChatController {
	    
    @Autowired
    public ChatService service;

    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/checkChatroom")
    public ResponseEntity<Integer> createChatRoom(@RequestParam("userId") String userId,@RequestParam("memberId") String memberId, ChatRoom room,ChatRoomJoin join){
    	
    	Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId); 
	    params.put("memberId",memberId);
    	
    	room.setUserId(userId);
    	room.setChatroomName(memberId);
    	int chatRoomNo = service.createChatRoom(room,params);
		
		if(chatRoomNo > 1) { 
			join.setUserId(memberId);
			join.setChatroomNo(chatRoomNo);
			int joinUs = service.joinRoom(join);
		}
    	return ResponseEntity.ok(chatRoomNo);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> messageRepo(@RequestParam("chatroomNo") int chatroomNo){
    	List<ChatMessage> messages = service.messageRepo(chatroomNo);
    	
    	return ResponseEntity.ok(messages);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/ChatSearch")
    public ResponseEntity<List<ChatRoom>> chatList(@RequestParam("query") String search,@RequestParam("userId") String userId){
    	Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId); 
	    params.put("search",search);
    	
    	List<ChatRoom> chatroom = service.searchChatlist(params);
        return ResponseEntity.ok(chatroom);
    }
    
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/ChatList")
    public ResponseEntity<List<ChatRoom>> chatListVo(@RequestParam("userId") String userId){
    	
    	List<ChatRoom> chatroom = service.Chatlist(userId);
        return ResponseEntity.ok(chatroom);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chatRoomFriend")
    public ResponseEntity<List<Member>> chatRoomFriend(@RequestParam("userId") String userId) {
        List<Member> members = service.chatRoomFriend(userId);
        return ResponseEntity.ok(members);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/insertGroupChat")
    public ResponseEntity<String> insertGroupChat(@RequestBody Map<String, Object> formData,ChatRoom room,ChatRoomJoin join){
    	String userId = (String) formData.get("userId");
        List<String> memberIdList = (List<String>) formData.get("memberId");
        String chatroomName = (String) formData.get("chatroomName");
    	
    	room.setUserId(userId);
    	room.setChatroomName(chatroomName);
    	int chatroomNo = service.insertGroupChat(room);
    	if(chatroomNo != 0) {
    		service.joinGroupMember(chatroomNo,memberIdList);
    	}
    	
    	return ResponseEntity.ok("");
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chatRoomMember")
    public ResponseEntity<List<Member>> chatRoomMember(@RequestParam("chatroomNo") int chatroomNo) {
        List<Member> members = service.chatRoomMember(chatroomNo);
        return ResponseEntity.ok(members);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/exitChatroom")
    public ResponseEntity<String> exitChatroom(@RequestParam("chatroomNo") int chatroomNo,@RequestParam("userId") String userId) {
    	Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId); 
	    params.put("chatroomNo",chatroomNo);
        int result = service.exitChatroom(params);
        if(result > 0) {
        	return ResponseEntity.ok("채팅방을 나갔습니다.");
        }else {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입에 실패하였습니다."); 
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/joinGroupChat")
    public ResponseEntity<String> joinGroupChat(@RequestBody Map<String, Object> formData,ChatRoomJoin join){
        List<String> memberIdList = (List<String>) formData.get("memberId");
    	int chatroomNo = (int)formData.get("chatroomNo");
    	
    	service.joinGroupMember(chatroomNo,memberIdList);
    	
    	return ResponseEntity.ok("");
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/followSearch")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam("query") String query,@RequestParam("userId") String userId) {
    	Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId); 
	    params.put("query",query);
	    
        List<Member> members = service.searchMembers(params);
        return ResponseEntity.ok(members);
    }
}
