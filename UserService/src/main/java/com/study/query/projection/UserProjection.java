package com.study.query.projection;


import com.study.CommonService.models.CardDetailsResponse;
import com.study.CommonService.models.UserResponse;
import com.study.CommonService.queries.GetUserPaymentDetailQuery;
import com.study.entity.CardDetails;
import com.study.entity.CardRepository;
import com.study.entity.User;
import com.study.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProjection {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    @QueryHandler
     public UserResponse getUserPaymentDetail (GetUserPaymentDetailQuery query){
            User user = userRepository.findById(query.getUserId()).get();
            CardDetails cardDetails = cardRepository.findByUser(user);
            CardDetailsResponse cardDetailsResponse = CardDetailsResponse.builder()
                    .cardNumber(cardDetails.getCardNumber())
                    .name(cardDetails.getName())
                    .validUntilMonth(cardDetails.getValidUntilMonth())
                    .validUntilYear(cardDetails.getValidUntilYear())
                    .cvv(cardDetails.getCvv())
                    .build();
            UserResponse userResponse = UserResponse.builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .cardDetailsResponse(cardDetailsResponse)
                    .build();
        return userResponse;
     }
}
