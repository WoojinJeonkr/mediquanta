package com.application.mediquanta.pharmacy.bookmark.controller;

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
import com.application.mediquanta.pharmacy.bookmark.dto.PharmacyBookmarkDTO;
import com.application.mediquanta.pharmacy.bookmark.service.PharmacyBookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bookmark/pharmacy")
@Tag(name="Pharmacy Bookmark", description="약국 북마크 Api")
public class PharmacyBookmarkController {

	@Autowired
	private PharmacyBookmarkService pharmacybookmarkService;

	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 북마크 조회 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "북마크한 약국 목록이 출력됩니다."
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
	@Operation(summary="페이지 이동 - 북마크 관리(약국 탭)", description="북마크 관리 페이지에서 약국 탭 클릭 시 약국 북마크 목록 조회")
	@PostMapping("/getPharmacyBookmarks")
	@ResponseBody
	public List<PharmacyBookmarkDTO> getPharmacyBookmarks(HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		List<PharmacyBookmarkDTO> bookmarkList = pharmacybookmarkService.getBookmarksForMember(memberId);
		return bookmarkList;
	}

	@ApiResponses(value= {
		@ApiResponse(
			responseCode = "200",
			description = "약국 북마크 추가"
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
	@Operation(summary="약국 북마크 추가", description="약국 조회 페이지에서 북마크 추가")
	@PostMapping("/addPharmacyBookmark")
	@ResponseBody
	public ResponseEntity<Void> addPharmacyBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long pharmacyId = request.get("pharmacyId");
		pharmacybookmarkService.addPharmacyBookmark(memberId, pharmacyId);
		return ResponseEntity.ok().build();
	}

	@ApiResponses(value= {
		@ApiResponse(
			responseCode = "200",
			description = "약국 북마크 제거"
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
	@Operation(summary="약국 북마크 제거", description="약국 조회 페이지에서 북마크 제거")
	@PostMapping("/removePharmacyBookmark")
	@ResponseBody
	public ResponseEntity<Void> removePharmacyBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long pharmacyId = request.get("pharmacyId");
		pharmacybookmarkService.removePharmacyBookmark(memberId, pharmacyId);
		return ResponseEntity.ok().build();
	}

}
