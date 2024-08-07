package com.application.mediquanta.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Error response for resource not found")
public class NotFoundError {

	@Schema(description = "Error message", example = "Requested resource not found")
    private String message;

    @Schema(description = "Error code", example = "404")
    private String errorCode;
    
}
