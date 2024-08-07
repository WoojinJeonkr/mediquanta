package com.application.mediquanta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.mediquanta.error.NotFoundError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name="Base Page", description="기본 페이지")
public class MediquantaController {

	@GetMapping
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "메인페이지 이동 성공",
				content = @Content(
						mediaType = "text/html",
			            schema = @Schema(
			                type = "string",
			                example = "<html><body><h1>메인 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 메인 페이지", description="메인 페이지로 이동")
	public String main() {
		return "main";
	}
	
	@GetMapping("/about")
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "사이트 소개 페이지 이동 성공",
				content = @Content(
					mediaType = "text/html",
		            schema = @Schema(
		                type = "string",
		                example = "<html><body><h1>사이트 소개 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 사이트 소개", description="사이트 소개 페이지로 이동")
	public String about() {
		return "about";
	}
	
	@GetMapping("/term")
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "이용약관 페이지 이동 성공",
				content = @Content(
						mediaType = "text/html",
			            schema = @Schema(
			                type = "string",
			                example = "<html><body><h1>이용약관 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 이용약관", description="이용약관 페이지로 이동")
	public String term() {
		return "term";
	}
	
	@GetMapping("/privacy")
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
				description = "개인정보처리방침 페이지 이동 성공",
				content = @Content(
						mediaType = "text/html",
			            schema = @Schema(
			                type = "string",
			                example = "<html><body><h1>개인정보처리방침 페이지로 성공적으로 이동했습니다.</h1></body></html>"
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
	@Operation(summary="페이지 이동 - 개인정보처리방침", description="개인정보처리방침 페이지로 이동")
	public String privacy() {
		return "privacy";
	}
	
}
