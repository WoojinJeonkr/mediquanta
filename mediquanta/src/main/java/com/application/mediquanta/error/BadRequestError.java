package com.application.mediquanta.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Error response for bad request")
public class BadRequestError {
	
	@Schema(description = "Error message")
    private String message;
    
    @Schema(description = "Error code")
    private String errorCode;

}
