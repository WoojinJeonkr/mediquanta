package com.application.mediquanta.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SearchData {

	@Schema(description = "병원 명칭")
	private String name;
	@Schema(description = "시도 코드 명")
    private String sidoCdNm;
	@Schema(description = "병원 유형")
    private String type;
    
}
