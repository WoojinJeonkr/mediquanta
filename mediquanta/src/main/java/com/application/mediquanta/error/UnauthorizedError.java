package com.application.mediquanta.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Error response for unauthorized access")
public class UnauthorizedError {
	
	@Schema(description = "Error message", example = "Authentication required")
    private String message;
    
    @Schema(description = "Error code", example = "401")
    private String errorCode;

}
