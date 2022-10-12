package com.example.jwt.domain.orderposition.dto;

import com.example.jwt.domain.orderposition.OrderPosition;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-12T10:17:37+0200",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class OrderPositionMapperImpl implements OrderPositionMapper {

    @Override
    public OnlyAmountDTO orderpositionToOnlyAmountDTO(OrderPosition orderPosition) {
        if ( orderPosition == null ) {
            return null;
        }

        OnlyAmountDTO onlyAmountDTO = new OnlyAmountDTO();

        onlyAmountDTO.setAmount( orderPosition.getAmount() );

        return onlyAmountDTO;
    }
}
