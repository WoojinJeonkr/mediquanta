package com.application.mediquanta.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
@Tag(name = "CustomErrorController", description = "HTTP 오류를 처리하고 적절한 오류 페이지를 반환하는 Api")
public class CustomErrorController implements ErrorController {

	@Operation(summary = "HTTP 오류를 처리하고 적절한 오류 페이지를 반환", description = "제공된 HTTP 오류 상태 코드에 해당하는 HTML 페이지를 반환")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401", description = "Unauthorized error page", content = @Content(mediaType = "text/html", schema = @Schema(type = "string", example = "<html><body><h1>401 Unauthorized</h1></body></html>"))),
			@ApiResponse(responseCode = "404", description = "Not Found error page", content = @Content(mediaType = "text/html", schema = @Schema(type = "string", example = "<html><body><h1>404 Not Found</h1></body></html>"))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error page", content = @Content(mediaType = "text/html", schema = @Schema(type = "string", example = "<html><body><h1>500 Internal Server Error</h1></body></html>"))),
			@ApiResponse(responseCode = "default", description = "Default error page (404 Not Found)", content = @Content(mediaType = "text/html", schema = @Schema(type = "string", example = "<html><body><h1>404 Not Found</h1></body></html>"))) })
	@GetMapping
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			switch (statusCode) {
			case 401:
				return "error/401";
			case 404:
				return "error/404";
			case 500:
				return "error/500";
			default:
				return "error/404";
			}
		}
		return "error/404";
	}

}
