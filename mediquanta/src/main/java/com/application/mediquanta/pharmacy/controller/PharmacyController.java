package com.application.mediquanta.pharmacy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.error.BadRequestError;
import com.application.mediquanta.error.NotFoundError;
import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.service.HospitalService;
import com.application.mediquanta.pharmacy.dto.PharmacyDTO;
import com.application.mediquanta.pharmacy.service.PharmacyService;
import com.application.mediquanta.util.SearchData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pharmacy")
@Tag(name="Pharmacy", description="약국 Api")
public class PharmacyController {

	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Operation(summary="건강정보심사평가원 Api를 통한 약국 정보를 가져온 뒤 DB에 저장", hidden=true)
	@GetMapping("/saveList")
	@ResponseBody
	public void savePharmacyList() {
		pharmacyService.savePharmacyListToDatabase();
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 조회 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>약국 조회 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 약국 조회", description="약국 조회 페이지로 이동")
	@GetMapping
	public String viewPharmacyList(Model model, HttpSession session) {
		model.addAttribute("role", (String) session.getAttribute("role"));
		return "pharmacy/pharmacyList";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 조회 성공",
			content = @Content(
				mediaType = "array",
	            schema = @Schema(
	                implementation=PharmacyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="약국 조회", description="약국 목록 가져오기")
	@PostMapping("/getPharmacyList")
	@ResponseBody
	public List<PharmacyDTO> getPharmacyList() {
		return pharmacyService.getPharmacyList();
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 조회 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=PharmacyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="약국 이름으로 약국 조회", description="약국 이름으로 약국 조회")
	@PostMapping("/searchPharmacyByName")
	@ResponseBody
	public List<PharmacyDTO> searchPharmacyByName(@RequestBody SearchData searchData) {
		return pharmacyService.searchPharmacyByName(searchData.getName());
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 조회 성공",
			content = @Content(
				mediaType = "array",
	            schema = @Schema(
	                implementation=PharmacyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="시도 코드 명으로 약국 조회", description="시도 코드 명으로 약국 조회")
	@PostMapping("/searchPharmacyBySidoCdNm")
	@ResponseBody
	public List<PharmacyDTO> searchPharmacyBySidoCdNm(@RequestBody SearchData searchData) {
		return pharmacyService.searchPharmacyBySidoCdNm(searchData.getSidoCdNm());
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 상세 조회 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=PharmacyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="약국 상세 조회", description="약국 상세 조회")
	@GetMapping("/details")
    public String viewPharmacyDetails(@RequestParam("pharmacyId") long pharmacyId, Model model) {
		PharmacyDTO pharmacyDTO = pharmacyService.getPharmacyDetails(pharmacyId);
		List<HospitalDTO> hospitals = hospitalService.selectNearestHospitals(pharmacyDTO.getLatitude(), pharmacyDTO.getLongitude());
        model.addAttribute("pharmacy", pharmacyDTO);
        model.addAttribute("hospitals", hospitals);
        return "pharmacy/pharmacyDetail";
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "약국 수정 페이지 이동 성공",
				content = @Content(
						mediaType = "text/html",
			            schema = @Schema(
			                type = "string",
			                example = "<html><body><h1>약국 수정 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 약국 수정", description="약국 수정 화면으로 이동")
	@GetMapping("/viewPharmacyUpdate")
	public String viewPharmacyUpdate(@RequestParam("pharmacyId") long pharmacyId, Model model) {
		PharmacyDTO pharmacyDTO = pharmacyService.getPharmacyDetails(pharmacyId);
		model.addAttribute("pharmacy", pharmacyDTO);
		return "pharmacy/pharmacyUpdate";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "약국 위치(위도, 경도) 조회 성공"
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="약국 위치 조회", description="약국 전체 주소를 통해 약국 위치(위도, 경도) 조회")
	@GetMapping("/findLoc")
	@ResponseBody
    public Map<String, Double> getLatitudeLongitude(@RequestParam("address") String address) {
        return pharmacyService.kakaoLocalAPI(address);
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "약국 정보 수정 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=PharmacyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="약국 정보 수정", description="약국 정보 수정")
	@PostMapping("/updatePharmacyInfo")
	public String updatePharmacyInfo(@ModelAttribute PharmacyDTO pharmacyDTO) {
		pharmacyService.updatePharmacyInfo(pharmacyDTO);
		return "redirect:/pharmacy";
	}
	
	@Operation(summary="약국 정보 삭제", description="약국 정보 삭제", hidden=true)
	@PostMapping("/deletePharmacy")
	public ResponseEntity<?> deletePharmacy(@RequestParam("pharmacyId") long pharmacyId) {
		pharmacyService.deletePharmacy(pharmacyId);
		return ResponseEntity.ok().build();
	}
}
