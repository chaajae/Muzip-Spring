package com.kh.muzip.member.model.service;

import java.util.List;
import java.util.Map;

import com.kh.muzip.member.model.vo.Follow;

public interface FollowService {


	List<Follow> getFollowsByUserId(String userId);

	List<Follow> getFollowerList(String userId);

	Map<String, Boolean> checkMultipleFollows(String userId, List<String> followerIds);

	
	
}
