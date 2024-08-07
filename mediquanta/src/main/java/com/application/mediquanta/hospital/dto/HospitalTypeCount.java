package com.application.mediquanta.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title="HospitalTypeCount DTO")
public class HospitalTypeCount {
	@Schema(description="병원 유형")
    private String type;
	@Schema(description="병원 유형에 대한 병원 수")
    private int count;
}
