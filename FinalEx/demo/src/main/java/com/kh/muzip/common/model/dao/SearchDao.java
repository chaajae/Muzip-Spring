package com.kh.muzip.common.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDao {
	
	@Autowired
	private SqlSessionTemplate session;

}
