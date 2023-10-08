package com.kh.muzip.chat.vo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALARM")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private String alarmNo;
   @Column(name = "RECEIVER_NO")
   private String receiverNo;
   @Column(name = "SENDER_NO")
   private String senderNo;
   @Column(name = "ALARM_KIND")
   private String alarmKind;
   @Column(name = "ALARM_MESSAGE")
   private String alarmMessage;
   @Column(name = "CREATE_DATE")
   private Date createDate;
   @Column(name = "ALARM_PATH")
   private String alarmPath;
   @Column(name = "CHECK_STATUS")
   private String checkStatus;
   @Column(name = "STATUS")
   private String status;
}