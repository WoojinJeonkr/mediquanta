package com.application.mediquanta.community.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.community.dao.CommunityDAO;
import com.application.mediquanta.community.dto.CommunityDTO;

@Service
public class CommunityServiceImpl implements CommunityService {
	
	@Autowired
	private CommunityDAO communityDAO;

	@Override
	public List<CommunityDTO> getCommunityList() {
		List<CommunityDTO> communityList = communityDAO.getCommunityList();
		return communityList.size() > 0 ? communityList : Collections.emptyList();
	}
	
	@Override
	public CommunityDTO findCommunity(long communityId) {
		return communityDAO.findCommunity(communityId);
	}
	
	@Override
	public CommunityDTO findCommunityByCommunityName(String communityName) {
		return communityDAO.findCommunityByCommunityName(communityName);
	}

	@Override
	public String validCommunityName(String communityName) {
		String getCommunityName = communityDAO.validCommunityName(communityName);
		return getCommunityName == null ? "y" : "n";
	}

	@Override
	public void createCommunity(CommunityDTO communityDTO) {
		communityDTO.setActiveYn("n");
		communityDTO.setCreatedAt(new Date());
		communityDAO.createCommunity(communityDTO);
	}

	@Override
	public Map<String, Long> countActiveCommunity() {
		List<Map<String, Object>> results = communityDAO.countActiveCommunity();
		Map<String, Long> activeYnCountMap = new HashMap<>();

		for (Map<String, Object> result : results) {
		    String activeYn = (String) result.get("ACTIVE_YN");
		    Long count = ((Number) result.get("count")).longValue();
		    activeYnCountMap.put(activeYn, count);
		}
		return activeYnCountMap;
	}

	@Override
	public void agreeCommunity(long communityId) {
		CommunityDTO community = communityDAO.findCommunity(communityId);
		community.setActiveYn("y");
		communityDAO.agreeCommunity(community);
	}
}
