package com.kh.muzip.setting.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory {
	
	private String paymentNo;
	private Date paymentDate;
	private String userNo;
	private String userId;
	private int membershipNo;
	private String membershipPrice;
}
