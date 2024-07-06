package com.application.mediquanta.reply.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.reply.dto.ReplyDTO;

@Mapper
public interface ReplyDAO {
	public List<ReplyDTO> findByPostId(Long postId);
	public List<Map<String, Object>> getReplyMemberCount();
	public void createReply(ReplyDTO replyDTO);
	public void updateReply(ReplyDTO replyDTO);
    public void deleteReply(Long replyId);
}
