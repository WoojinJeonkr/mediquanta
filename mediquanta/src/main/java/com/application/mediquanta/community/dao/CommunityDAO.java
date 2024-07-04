package com.application.mediquanta.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.community.dto.CommunityDTO;

@Mapper
public interface CommunityDAO {

	public List<CommunityDTO> getCommunityList();
	public CommunityDTO findCommunity(long communityId);
	public String validCommunityName(String communityName);
	public void createCommunity(CommunityDTO communityDTO);
	public List<Map<String, Object>> countActiveCommunity();
	public void agreeCommunity(CommunityDTO communityDTO);
}
