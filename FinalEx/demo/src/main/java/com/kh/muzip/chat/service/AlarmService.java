package com.kh.muzip.chat.service;

import java.util.ArrayList;
import java.util.List;

import com.kh.muzip.chat.vo.Alarm;

public interface AlarmService {
	List<Alarm> getAlarms(String userNo);
	String getUserId(String userNo);
	String insertChatAlarm(Alarm alarm);
	String insertFollowAlarm(Alarm alarm);
	String insertReplyAlarm(Alarm alarm);
	int checkAlarm(String alarmNo);
	int removeChatRoomAlarm(String chatroomNo, String userNo);
	String getReplyReceiverNo(String boardNo);
	String searchUserNo(String userId);
}
