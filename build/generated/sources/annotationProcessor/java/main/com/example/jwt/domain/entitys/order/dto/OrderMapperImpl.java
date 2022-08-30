package com.example.jwt.domain.entitys.order.dto;

import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.country.dto.CountryDTO;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.teas.Tea;
import com.example.jwt.domain.entitys.teas.dto.TeaDTO;
import com.example.jwt.domain.entitys.teatype.TeaType;
import com.example.jwt.domain.entitys.teatype.dto.TeaTypeDTO;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.entitys.user.dto.UserDTO;
import com.example.jwt.domain.orderposition.OrderPosition;
import com.example.jwt.domain.orderposition.dto.OrderPositionDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-30T12:13:32+0200",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order fromDTO(OrderCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( dto.getId() );
        order.setUser( userDTOToUser( dto.getUser() ) );
        order.setOrderPositions( orderPositionDTOSetToOrderPositionSet( dto.getOrderPositions() ) );

        return order;
    }

    @Override
    public List<Order> fromDTOs(List<OrderCreateDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( dtos.size() );
        for ( OrderCreateDTO orderCreateDTO : dtos ) {
            list.add( fromDTO( orderCreateDTO ) );
        }

        return list;
    }

    @Override
    public Set<Order> fromDTOs(Set<OrderCreateDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<Order> set = new LinkedHashSet<Order>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( OrderCreateDTO orderCreateDTO : dtos ) {
            set.add( fromDTO( orderCreateDTO ) );
        }

        return set;
    }

    @Override
    public OrderCreateDTO toDTO(Order BO) {
        if ( BO == null ) {
            return null;
        }

        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();

        orderCreateDTO.setId( BO.getId() );
        orderCreateDTO.setUser( userToUserDTO( BO.getUser() ) );
        orderCreateDTO.setOrderPositions( orderPositionSetToOrderPositionDTOSet( BO.getOrderPositions() ) );

        return orderCreateDTO;
    }

    @Override
    public List<OrderCreateDTO> toDTOs(List<Order> BOs) {
        if ( BOs == null ) {
            return null;
        }

        List<OrderCreateDTO> list = new ArrayList<OrderCreateDTO>( BOs.size() );
        for ( Order order : BOs ) {
            list.add( toDTO( order ) );
        }

        return list;
    }

    @Override
    public Set<OrderCreateDTO> toDTOs(Set<Order> BOs) {
        if ( BOs == null ) {
            return null;
        }

        Set<OrderCreateDTO> set = new LinkedHashSet<OrderCreateDTO>( Math.max( (int) ( BOs.size() / .75f ) + 1, 16 ) );
        for ( Order order : BOs ) {
            set.add( toDTO( order ) );
        }

        return set;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setSeeds( userDTO.getSeeds() );
        user.setFirstName( userDTO.getFirstName() );
        user.setLastName( userDTO.getLastName() );
        user.setAge( userDTO.getAge() );
        user.setEmail( userDTO.getEmail() );
        user.setRank( userDTO.getRank() );

        return user;
    }

    protected TeaType teaTypeDTOToTeaType(TeaTypeDTO teaTypeDTO) {
        if ( teaTypeDTO == null ) {
            return null;
        }

        TeaType teaType = new TeaType();

        teaType.setId( teaTypeDTO.getId() );

        return teaType;
    }

    protected Country countryDTOToCountry(CountryDTO countryDTO) {
        if ( countryDTO == null ) {
            return null;
        }

        Country country = new Country();

        country.setId( countryDTO.getId() );
        country.setCountry( countryDTO.getCountry() );

        return country;
    }

    protected Tea teaDTOToTea(TeaDTO teaDTO) {
        if ( teaDTO == null ) {
            return null;
        }

        Tea tea = new Tea();

        tea.setId( teaDTO.getId() );
        tea.setDescription( teaDTO.getDescription() );
        tea.setPrice( teaDTO.getPrice() );
        tea.setHarvestDate( teaDTO.getHarvestDate() );
        tea.setTeaType( teaTypeDTOToTeaType( teaDTO.getTeaType() ) );
        tea.setCountry( countryDTOToCountry( teaDTO.getCountry() ) );

        return tea;
    }

    protected OrderPosition orderPositionDTOToOrderPosition(OrderPositionDTO orderPositionDTO) {
        if ( orderPositionDTO == null ) {
            return null;
        }

        OrderPosition orderPosition = new OrderPosition();

        orderPosition.setId( orderPositionDTO.getId() );
        orderPosition.setAmount( orderPositionDTO.getAmount() );
        orderPosition.setTea( teaDTOToTea( orderPositionDTO.getTea() ) );

        return orderPosition;
    }

    protected Set<OrderPosition> orderPositionDTOSetToOrderPositionSet(Set<OrderPositionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<OrderPosition> set1 = new LinkedHashSet<OrderPosition>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( OrderPositionDTO orderPositionDTO : set ) {
            set1.add( orderPositionDTOToOrderPosition( orderPositionDTO ) );
        }

        return set1;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setSeeds( user.getSeeds() );
        userDTO.setRank( user.getRank() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setAge( user.getAge() );
        userDTO.setEmail( user.getEmail() );

        return userDTO;
    }

    protected TeaTypeDTO teaTypeToTeaTypeDTO(TeaType teaType) {
        if ( teaType == null ) {
            return null;
        }

        TeaTypeDTO teaTypeDTO = new TeaTypeDTO();

        teaTypeDTO.setId( teaType.getId() );

        return teaTypeDTO;
    }

    protected CountryDTO countryToCountryDTO(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setId( country.getId() );
        countryDTO.setCountry( country.getCountry() );

        return countryDTO;
    }

    protected TeaDTO teaToTeaDTO(Tea tea) {
        if ( tea == null ) {
            return null;
        }

        TeaDTO teaDTO = new TeaDTO();

        teaDTO.setId( tea.getId() );
        teaDTO.setDescription( tea.getDescription() );
        teaDTO.setPrice( tea.getPrice() );
        teaDTO.setHarvestDate( tea.getHarvestDate() );
        teaDTO.setTeaType( teaTypeToTeaTypeDTO( tea.getTeaType() ) );
        teaDTO.setCountry( countryToCountryDTO( tea.getCountry() ) );

        return teaDTO;
    }

    protected OrderPositionDTO orderPositionToOrderPositionDTO(OrderPosition orderPosition) {
        if ( orderPosition == null ) {
            return null;
        }

        OrderPositionDTO orderPositionDTO = new OrderPositionDTO();

        orderPositionDTO.setId( orderPosition.getId() );
        orderPositionDTO.setAmount( orderPosition.getAmount() );
        orderPositionDTO.setTea( teaToTeaDTO( orderPosition.getTea() ) );

        return orderPositionDTO;
    }

    protected Set<OrderPositionDTO> orderPositionSetToOrderPositionDTOSet(Set<OrderPosition> set) {
        if ( set == null ) {
            return null;
        }

        Set<OrderPositionDTO> set1 = new LinkedHashSet<OrderPositionDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( OrderPosition orderPosition : set ) {
            set1.add( orderPositionToOrderPositionDTO( orderPosition ) );
        }

        return set1;
    }
}
