package com.application.mediquanta.reply.service;

import java.util.List;

import com.application.mediquanta.reply.dto.ReplyDTO;

public interface ReplyService {

	public List<ReplyDTO> getRepliesByPostId(long postId);
	public long getReplyMemberCount(String communityName);
    public void createReply(ReplyDTO replyDTO, String communityName);
    public void updateReply(ReplyDTO replyDTO);
    public void deleteReply(long postId, long replyId);
    
}
