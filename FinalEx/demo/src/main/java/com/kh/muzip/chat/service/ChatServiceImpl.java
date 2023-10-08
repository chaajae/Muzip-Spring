package com.kh.muzip.chat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.muzip.chat.dao.ChatDao;
import com.kh.muzip.chat.vo.ChatMessage;
import com.kh.muzip.chat.vo.ChatRoom;
import com.kh.muzip.chat.vo.ChatRoomJoin;
import com.kh.muzip.member.model.vo.Member;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatDao chatDao;
	
	@Override
	public int createChatRoom(ChatRoom room,Map<String, Object> params) {
		return chatDao.createChatRoom(room,params);
	}
	
	@Override
	public int joinRoom(ChatRoomJoin join) {
		return chatDao.joinRoom(join);
	}
	
	@Override
	public int insertMsg(ChatMessage message) {
		return chatDao.insertMsg(message);
	}
	
	@Override
	public List<ChatMessage> messageRepo(int chatroomNo){
		return chatDao.messageRepo(chatroomNo);
	}
	
	@Override
	public List<ChatRoom> searchChatlist(Map<String, Object> params){
		return chatDao.searchChatlist(params);
	}
	
	@Override
	public List<ChatRoom> Chatlist(String userId){
		return chatDao.Chatlist(userId);
	}
	
	@Override
	public List<Member> chatRoomFriend(String userId){
		return chatDao.chatRoomFriend(userId);
	}
	
	@Override
	public int insertGroupChat(ChatRoom room) {
		return chatDao.insertGroupChat(room);
	}
	
	@Override
	public Object joinGroupMember(int chatroomNo, List<String> memberIdList) {
		return chatDao.joinGroupMember(chatroomNo,memberIdList);
	}
	
	@Override
	public List<Member> chatRoomMember(int chatroomNo){
		return chatDao.chatRoomMember(chatroomNo);
	}
	
	@Override
	public int exitChatroom(Map<String, Object> params) {
		return chatDao.exitChatroom(params);
	}
	
	@Override
	public List<Member> searchMembers(Map<String, Object> params){
		return chatDao.searchMembers(params);
	}
}
