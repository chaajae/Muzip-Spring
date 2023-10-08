package com.kh.muzip.member.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.muzip.member.model.vo.Follow;

@Repository
public class FollowDao {
    @Autowired
    private SqlSessionTemplate session;

 

	public List<Follow> getFollowsByUserId(String userId) {
		return session.selectList("follow.getFollowsByUserId", userId);
	}



	public List<Follow> getFollowerList(String userId) {
		return session.selectList("follow.getFollowerList", userId);
	}


	public Follow getFollowByUserIdAndMemberId(String userId, String memberId) {
	    Map<String, String> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("memberId", memberId);
	   
	    
	    return session.selectOne("follow.getFollowByUserIdAndMemberId", params);
	    
	    
	}



	
	
	
}
