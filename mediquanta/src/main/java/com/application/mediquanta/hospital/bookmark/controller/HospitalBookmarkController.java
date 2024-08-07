package com.application.mediquanta.hospital.bookmark.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.error.BadRequestError;
import com.application.mediquanta.error.NotFoundError;
import com.application.mediquanta.hospital.bookmark.dto.HospitalBookmarkDTO;
import com.application.mediquanta.hospital.bookmark.service.HospitalBookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bookmark/hospital")
@Tag(name="Hospital Bookmark", description="병원 북마크 Api")
public class HospitalBookmarkController {

	@Autowired
	private HospitalBookmarkService hospitalBookmarkService;

	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 북마크 조회 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "북마크한 병원 목록이 출력됩니다."
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 북마크 관리(병원 탭)", description="북마크 관리 페이지에서 병원 탭 클릭 시 병원 북마크 목록 조회")
	@PostMapping("/getHospitalBookmarks")
	@ResponseBody
	public List<HospitalBookmarkDTO> getHospitalBookmarks(HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		List<HospitalBookmarkDTO> bookmarkList = hospitalBookmarkService.getBookmarksForMember(memberId);
		return bookmarkList;
	}

	@ApiResponses(value= {
		@ApiResponse(
			responseCode = "200",
			description = "병원 북마크 추가"
		),
		@ApiResponse(
	        responseCode = "400",
	        description = "잘못된 요청입니다.",
	        content = @Content(
	            schema = @Schema(
	                implementation = BadRequestError.class
	            ),
	            examples = @ExampleObject(
	                name = "Bad Request Example",
	                value = "{ \"message\": \"잘못된 요청입니다.\", \"errorCode\": \"400\" }"
	            )
	        )
	    )
	})
	@Operation(summary="병원 북마크 추가", description="병원 조회 페이지에서 북마크 추가")
	@PostMapping("/addHospitalBookmark")
	@ResponseBody
	public ResponseEntity<Void> addHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long hospitalId = request.get("hospitalId");
		hospitalBookmarkService.addHospitalBookmark(memberId, hospitalId);
		return ResponseEntity.ok().build();
	}

	@ApiResponses(value= {
		@ApiResponse(
			responseCode = "200",
			description = "병원 북마크 제거"
		),
		@ApiResponse(
	        responseCode = "400",
	        description = "잘못된 요청입니다.",
	        content = @Content(
	            schema = @Schema(
	                implementation = BadRequestError.class
	            ),
	            examples = @ExampleObject(
	                name = "Bad Request Example",
	                value = "{ \"message\": \"잘못된 요청입니다.\", \"errorCode\": \"400\" }"
	            )
	        )
	    )
	})
	@Operation(summary="병원 북마크 제거", description="병원 조회 페이지에서 북마크 제거")
	@PostMapping("/removeHospitalBookmark")
	@ResponseBody
	public ResponseEntity<Void> removeHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long hospitalId = request.get("hospitalId");
		hospitalBookmarkService.removeHospitalBookmark(memberId, hospitalId);
		return ResponseEntity.ok().build();
	}

}
