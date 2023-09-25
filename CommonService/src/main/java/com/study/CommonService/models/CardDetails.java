package com.study.CommonService.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardDetails {
    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;
}
