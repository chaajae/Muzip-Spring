package com.kh.muzip.member.model.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.muzip.member.model.dao.FollowDao;
import com.kh.muzip.member.model.vo.Follow;

@Service
public class FollowServiceImpl implements FollowService{

	 @Autowired
	    private FollowDao followDAO;

	   
		@Override
		public List<Follow> getFollowsByUserId(String userId) {
			return followDAO.getFollowsByUserId(userId);
		}


		@Override
		public List<Follow> getFollowerList(String userId) {
			return followDAO.getFollowerList(userId);
		}
		
		 @Override
		    @Transactional
		    public Map<String, Boolean> checkMultipleFollows(String userId, List<String> followerIds) {
		        Map<String, Boolean> resultMap = new HashMap<>();

		        for (String followerId : followerIds) {
		            Follow follow = followDAO.getFollowByUserIdAndMemberId(userId, followerId);
		            if (follow != null && "Y".equals(follow.getStatus())) {
		                resultMap.put(followerId, true);
		            } else {
		                resultMap.put(followerId, false);
		            }
		        }
		        
		        return resultMap;
		    }


		
}
