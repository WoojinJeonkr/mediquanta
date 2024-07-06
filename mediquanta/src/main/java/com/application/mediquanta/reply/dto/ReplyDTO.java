package com.application.mediquanta.reply.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private Long replyId;
    private Long postId;
    private String communityName;
    private String nickname;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
