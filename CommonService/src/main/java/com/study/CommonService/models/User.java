package com.study.CommonService.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String UserId;
    private String firstName;
    private String lastName;
    private CardDetails cardDetails;
}
