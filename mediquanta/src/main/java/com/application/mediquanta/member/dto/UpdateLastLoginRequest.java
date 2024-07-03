package com.application.mediquanta.member.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateLastLoginRequest {
	private String memberId;
    private Date lastLogin;
}
