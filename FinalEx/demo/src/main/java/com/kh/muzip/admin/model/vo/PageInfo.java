package com.kh.muzip.admin.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
	int listCount; // 현재 총 게시글 갯수(1000개)
	int currentPage; // 현재 페이지(즉, 사용자가 요청한 페이지)
	int pageLimit; // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수(10개씩 할예정)
	int boardLimit; // 한 페이지에 보여질 게시글의 최대 갯수(10개씩 할예정)
	int maxPage; // 가장 마지막 페이지가 몇번째 페이지인지(총 페이지 개수)
	int startPage; // 페이지 하단에 보여질 페이징바의 시작수
	int endPage; // 페이지 하단에 보여질 페이징바의 끝수
}
