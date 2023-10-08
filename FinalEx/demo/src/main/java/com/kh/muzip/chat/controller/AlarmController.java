package com.kh.muzip.chat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.muzip.chat.service.AlarmService;
import com.kh.muzip.chat.vo.Alarm;

@RestController
@CrossOrigin(origins = "http://192.168.30.180:3000")
public class AlarmController {
	
	@Autowired
	private AlarmService alarmService;
	
	@CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAlarms")
    public List<Alarm> getAlarms(@RequestParam("userNo") String userNo){
    			
		List<Alarm> list = alarmService.getAlarms(userNo);
    	
    	return list;
    }
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/checkAlarm")
	public int checkAlarm(@RequestParam("alarmNo") String alarmNo){
		return alarmService.checkAlarm(alarmNo);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/removeChatRoomAlarm")
	public int removeChatRoomAlarm(
			@RequestParam("chatroomNo") String chatroomNo,
			@RequestParam("userNo") String userNo
			){
		return alarmService.removeChatRoomAlarm(chatroomNo, userNo);
	}

}
