package com.kh.muzip.chat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.muzip.chat.vo.ChatMessage;
import com.kh.muzip.chat.vo.ChatRoom;
import com.kh.muzip.chat.vo.ChatRoomJoin;
import com.kh.muzip.member.model.vo.Member;

@Repository
public class ChatDao {

	@Autowired
	private SqlSessionTemplate session;
	
	public int createChatRoom(ChatRoom room,Map<String, Object> params) {
		//방이 있나 확인 1 or 0
		int resultint=0;
		
		int check = session.selectOne("chat.checkRoom",params);
		int check2 = session.selectOne("chat.checkRoomOther",params);
		//0일때 
		if(check == 0 && check2 == 0) {
			// 채팅방 생성
			int result = session.insert("chat.createChatRoom",room);
			// 성공시 채팅방안에 사용자 정보 추가
			if(result > 0) {
				Map<String, Object> scparams = new HashMap<>();
				scparams.put("result", room.getChatroomNo()); // 정수 값
				scparams.put("room", room);
				session.insert("chat.joinChatRoom",scparams);
				check = room.getChatroomNo();
				resultint =  check;
			}
		// 0이 아닐때
		}else {
			int checked = session.selectOne("chat.checkedRoom",params);
			resultint =  checked;
		}
		return resultint;
	}
	
	public int joinRoom(ChatRoomJoin join) {
		int check = session.selectOne("chat.checkjoinRoom",join);
		if(check == 0) {
			int result = session.insert("chat.joinRoom",join);
			return result;
		}else {
			return 0;
		}
		
		
	}

	public int insertMsg(ChatMessage message) {
		int result = session.insert("chat.insertMsg",message);
		return result;
	}

	public List<ChatMessage> messageRepo(int chatroomNo) {
		return session.selectList("chat.messageRepo",chatroomNo);
	}

	public List<ChatRoom> searchChatlist(Map<String, Object> params) {
		return session.selectList("chat.searchChat",params);
	}
	
	public List<ChatRoom> Chatlist(String userId){
		return session.selectList("chat.Chatlist",userId);
	}

	public List<Member> chatRoomFriend(String userId) {
		return session.selectList("follow.chatRoomFriend",userId);
	}

	public int insertGroupChat(ChatRoom room) {
		int result = 0;
		int check = session.insert("chat.createGroupChatRoom",room);
		if(check == 1) {
			Map<String, Object> params = new HashMap<>();
			params.put("chatroomNo", room.getChatroomNo());
			params.put("memberId", room.getUserId());
			session.insert("chat.joinGroupMember",params);
			return result = room.getChatroomNo();
		}
		return result; 
	}

	public Object joinGroupMember(int chatroomNo, List<String> memberIdList) {
		Map<String, Object> params = new HashMap<>();
		for(String memberId : memberIdList) {
			params.put("chatroomNo", chatroomNo);
			params.put("memberId", memberId);
			session.insert("chat.joinGroupMember",params);
		}
		return null;
	}

	public List<Member> chatRoomMember(int chatroomNo) {
		return session.selectList("member.chatRoomMember",chatroomNo);
	}

	public int exitChatroom(Map<String, Object> params) {
		return session.delete("chat.exitChatroom",params);
	}

	public List<Member> searchMembers(Map<String, Object> params) {
		return session.selectList("follow.searchMembers",params);
	}
}
