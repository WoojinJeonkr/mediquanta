package com.application.mediquanta.reply.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.post.dao.PostDAO;
import com.application.mediquanta.reply.dao.ReplyDAO;
import com.application.mediquanta.reply.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private ReplyDAO replyDAO;
	
	@Override
	public List<ReplyDTO> getRepliesByPostId(Long postId) {
		List<ReplyDTO> replyList = replyDAO.findByPostId(postId);
		if (replyList != null) {
			return replyDAO.findByPostId(postId).stream().map(reply -> {
	            ReplyDTO replyDTO = new ReplyDTO();
	            replyDTO.setReplyId(reply.getReplyId());
	            replyDTO.setPostId(reply.getPostId());
	            replyDTO.setNickname(reply.getNickname());
	            replyDTO.setContent(reply.getContent());
	            replyDTO.setCreatedAt(reply.getCreatedAt());
	            replyDTO.setUpdatedAt(reply.getUpdatedAt());
	            return replyDTO;
	        }).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}
	
	@Override
	public long getReplyMemberCount(String communityName) {
		List<Map<String, Object>> replyMemberCounts = replyDAO.getReplyMemberCount();
	    for (Map<String, Object> replyMemberCount : replyMemberCounts) {
	        if (communityName.equals(replyMemberCount.get("COMMUNITY_NAME"))) {
	            return Long.parseLong(replyMemberCount.get("COUNT").toString());
	        }
	    }
	    return 0;
	}

	@Override
	public void createReply(ReplyDTO replyDTO, String communityName) {
		replyDTO.setCommunityName(communityName);
		replyDTO.setCreatedAt(new Date());
		postDAO.updateReplyCntPlus(replyDTO.getPostId());
        replyDAO.createReply(replyDTO);
	}

	@Override
	public void updateReply(ReplyDTO replyDTO) {
		replyDTO.setUpdatedAt(new Date());
		replyDAO.updateReply(replyDTO);
	}

	@Override
	public void deleteReply(long postId, long replyId) {
		postDAO.updateReplyCntMinus(postId);
		replyDAO.deleteReply(replyId);
	}

}
