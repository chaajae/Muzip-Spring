package com.kh.muzip.chat.vo;


import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name = "CHAT_MSG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "MSG_NO")
   private int msgNo;
   @Column(name = "CHATROOM_NO")
   private String chatroomNo;
   @Column(name = "USER_NO")
   private String senderName;
   @Column(name = "MESSAGE")
   private String message;
   @Column(name = "CREATE_DATE")
   private Timestamp createDate;
}



