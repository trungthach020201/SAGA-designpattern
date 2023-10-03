package com.study.query.projection;

import com.study.CommonService.models.CardDetails;
import com.study.CommonService.models.User;
import com.study.CommonService.queries.GetUserPaymentDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {


    @QueryHandler
     public User getUserPaymentDetail (GetUserPaymentDetailQuery query){
        System.out.println(query.getUserId());

         CardDetails cardDetails = CardDetails.builder()
                 .cardNumber("123456789")
                 .name("Trung Thach")
                 .validUntilMonth(10)
                 .validUntilYear(2027)
                 .cvv(569)
                 .build();
        return User.builder()
                 .userId("123456")
                 .cardDetails(cardDetails)
                 .firstName("Thach")
                 .lastName("Trung")
                 .build();
     }
}
