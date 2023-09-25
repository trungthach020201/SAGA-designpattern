package com.study.UserService.query.projection;

import com.study.CommonService.models.CardDetails;
import com.study.CommonService.models.User;
import com.study.CommonService.queries.GetUserPaymentDetailQuery;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

     public User getUserPaymentDetail (GetUserPaymentDetailQuery query){
         CardDetails cardDetails = CardDetails.builder()
                 .cardNumber("123456789")
                 .name("Trung Thach")
                 .validUntilMonth(10)
                 .validUntilYear(2027)
                 .cvv(569)
                 .build();
         User user = User.builder()
                 .UserId(query.getUserId())
                 .cardDetails(cardDetails)
                 .firstName("Thach")
                 .lastName("Trung")

                 .build();
         return user;
     }
}
