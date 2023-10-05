package com.study.CommonService.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardDetailsResponse {
    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;
}
