package com.kh.muzip.chat.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.muzip.chat.vo.Alarm;

@Repository
public class AlarmDao {
   
   @Autowired
   private SqlSessionTemplate session;
   
   public List<Alarm> getAlarms(String userNo){
	   
      List<Alarm> list = session.selectList("alarmMapper.getAlarms", userNo) ;

      return list;
   }
   
   public String getUserId(String userNo) {
      return session.selectOne("alarmMapper.getUserId", userNo);
   }
   
   public String insertChatAlarm(Alarm alarm) {
      session.insert("alarmMapper.insertChatAlarm", alarm);
      return alarm.getAlarmNo();
   }
   public String insertFollowAlarm(Alarm alarm) {
      session.insert("alarmMapper.insertFollowAlarm", alarm);
      return alarm.getAlarmNo();
   }
   public String insertReplyAlarm(Alarm alarm) {
      // boardNo 받아오는 과정 추가
      session.insert("alarmMapper.insertReplyAlarm", alarm);
      return alarm.getAlarmNo();
   }
   
   public int checkAlarm(String alarmNo) {
      return session.update("alarmMapper.checkAlarm", alarmNo);
   }
   
   public int removeChatRoomAlarm(String chatroomNo, String userNo) {
	  Map<String, String> map = new HashMap<>();
	  map.put("chatroomNo", chatroomNo);
	  map.put("userNo", userNo);
	  return session.update("alarmMapper.removeChatRoomAlarm", map);
   }
   
   public String getReplyReceiverNo(String boardNo) {
      return session.selectOne("alarmMapper.getReplyReceiverNo", boardNo);
   }

   public String searchUserNo(String userId) {
      return session.selectOne("alarmMapper.searchUserNo", userId);
   }
}