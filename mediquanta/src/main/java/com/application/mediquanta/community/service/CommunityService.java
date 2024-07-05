package com.application.mediquanta.community.service;

import java.util.List;
import java.util.Map;

import com.application.mediquanta.community.dto.CommunityDTO;

public interface CommunityService {

	public List<CommunityDTO> getCommunityList();
	public CommunityDTO findCommunity(long communityId);
	public CommunityDTO findCommunityByCommunityName(String communityName);
	public String validCommunityName(String communityName);
	public void createCommunity(CommunityDTO communityDTO);
	public Map<String, Long> countActiveCommunity();
	public void agreeCommunity(long communityId);
	
}
