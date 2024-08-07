package com.application.mediquanta.hospital.controller;

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
@RequestMapping("/hospital")
@Tag(name="Hospital", description="병원 Api")
public class HospitalController {
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Operation(summary="건강정보심사평가원 Api를 통한 병원 정보를 가져온 뒤 DB에 저장", hidden=true)
	@GetMapping("/saveList")
	@ResponseBody
	public void saveHospitalList() {
		hospitalService.saveHospitalsToDatabase();
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 조회 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>병원 조회 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 병원 조회", description="병원 조회 페이지로 이동")
	@GetMapping
	public String viewHospitalList(Model model, HttpSession session) {
		model.addAttribute("role", (String)session.getAttribute("role"));
		return "hospital/hospitalList";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 조회 성공",
			content = @Content(
				mediaType = "array",
	            schema = @Schema(
	                implementation=HospitalDTO.class
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
	@Operation(summary="병원 조회", description="병원 목록 가져오기")
	@PostMapping("/getHospitalList")
	@ResponseBody
	public List<HospitalDTO> getHospitalList() {
		return hospitalService.getHospitalList();
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 조회 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=HospitalDTO.class
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
	@Operation(summary="병원 이름으로 병원 조회", description="병원 이름으로 병원 조회")
	@PostMapping("/searchHospitalByName")
	@ResponseBody
	public List<HospitalDTO> searchHospitalByName(@RequestBody SearchData searchData) {
		return hospitalService.searchHospitalByName(searchData.getName());
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 조회 성공",
			content = @Content(
				mediaType = "array",
	            schema = @Schema(
	                implementation=HospitalDTO.class
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
	@Operation(summary="시도 코드 명으로 병원 조회", description="시도 코드 명으로 병원 조회")
	@PostMapping("/searchHospitalBySidoCdNm")
	@ResponseBody
	public List<HospitalDTO> searchHospitalBySidoCdNm(@RequestBody SearchData searchData) {
		return hospitalService.searchHospitalBySidoCdNm(searchData.getSidoCdNm());
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 조회 성공",
			content = @Content(
				mediaType = "array",
	            schema = @Schema(
	                implementation=HospitalDTO.class
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
	@Operation(summary="병원 유형으로 병원 조회", description="병원 유형으로 병원 조회")
	@PostMapping("/searchHospitalByType")
	@ResponseBody
	public List<HospitalDTO> searchHospitalByType(@RequestBody SearchData searchData) {
		return hospitalService.searchHospitalByType(searchData.getType());
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 상세 조회 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=HospitalDTO.class
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
	@Operation(summary="병원 상세 조회", description="병원 상세 조회")
	@GetMapping("/details")
    public String viewHospitalDetails(@RequestParam("hospitalId") long hospitalId, Model model) {
        HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(hospitalId);
        List<PharmacyDTO> pharmacies = pharmacyService.selectNearestPharmacies(hospitalDTO.getLatitude(), hospitalDTO.getLongitude());
        model.addAttribute("hospital", hospitalDTO);
        model.addAttribute("pharmacies", pharmacies);
        return "hospital/hospitalDetail";
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "병원 수정 페이지 이동 성공",
				content = @Content(
						mediaType = "text/html",
			            schema = @Schema(
			                type = "string",
			                example = "<html><body><h1>병원 수정 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 병원 수정", description="병원 수정 화면으로 이동")
	@GetMapping("/viewHospitalUpdate")
	public String viewHospitalUpdate(@RequestParam("hospitalId") long hospitalId, Model model) {
		HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(hospitalId);
		model.addAttribute("hospital", hospitalDTO);
		return "hospital/hospitalUpdate";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "병원 위치(위도, 경도) 조회 성공"
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
	@Operation(summary="병원 위치 조회", description="병원 전체 주소를 통해 병원 위치(위도, 경도) 조회")
	@GetMapping("/findLoc")
	@ResponseBody
    public Map<String, Double> getLatitudeLongitude(@RequestParam("address") String address) {
        return hospitalService.kakaoLocalAPI(address);
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "병원 정보 수정 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=HospitalDTO.class
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
	@Operation(summary="병원 정보 수정", description="병원 정보 수정")
	@PostMapping("/updateHospInfo")
	public String updateHospInfo(@ModelAttribute HospitalDTO hospitalDTO) {
		hospitalService.updateHospInfo(hospitalDTO);
		return "redirect:/hospital";
	}
	
	@Operation(summary="병원 정보 삭제", description="병원 정보 삭제", hidden=true)
	@PostMapping("/deleteHospital")
	public ResponseEntity<?> deleteHospital(@RequestParam("hospitalId") long hospitalId) {
		hospitalService.deleteHospital(hospitalId);
		return ResponseEntity.ok().build();
	}
}
