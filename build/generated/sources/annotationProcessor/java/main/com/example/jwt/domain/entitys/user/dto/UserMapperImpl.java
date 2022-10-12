package com.example.jwt.domain.entitys.user.dto;

import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.authority.dto.AuthorityDTO;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.role.Role;
import com.example.jwt.domain.role.dto.RoleDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-12T10:17:37+0200",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDTO(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setSeeds( dto.getSeeds() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setAge( dto.getAge() );
        user.setEmail( dto.getEmail() );
        user.setRank( dto.getRank() );

        return user;
    }

    @Override
    public List<User> fromDTOs(List<UserDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtos.size() );
        for ( UserDTO userDTO : dtos ) {
            list.add( fromDTO( userDTO ) );
        }

        return list;
    }

    @Override
    public Set<User> fromDTOs(Set<UserDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<User> set = new LinkedHashSet<User>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( UserDTO userDTO : dtos ) {
            set.add( fromDTO( userDTO ) );
        }

        return set;
    }

    @Override
    public UserDTO toDTO(User BO) {
        if ( BO == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( BO.getId() );
        userDTO.setSeeds( BO.getSeeds() );
        userDTO.setRank( BO.getRank() );
        userDTO.setFirstName( BO.getFirstName() );
        userDTO.setLastName( BO.getLastName() );
        userDTO.setAge( BO.getAge() );
        userDTO.setEmail( BO.getEmail() );

        return userDTO;
    }

    @Override
    public List<UserDTO> toDTOs(List<User> BOs) {
        if ( BOs == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( BOs.size() );
        for ( User user : BOs ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public Set<UserDTO> toDTOs(Set<User> BOs) {
        if ( BOs == null ) {
            return null;
        }

        Set<UserDTO> set = new LinkedHashSet<UserDTO>( Math.max( (int) ( BOs.size() / .75f ) + 1, 16 ) );
        for ( User user : BOs ) {
            set.add( toDTO( user ) );
        }

        return set;
    }

    @Override
    public User fromUserRegisterDTO(UserRegisterDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setAge( dto.getAge() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );
        user.setRoles( roleDTOSetToRoleSet( dto.getRoles() ) );

        return user;
    }

    protected Authority authorityDTOToAuthority(AuthorityDTO authorityDTO) {
        if ( authorityDTO == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setId( authorityDTO.getId() );
        authority.setName( authorityDTO.getName() );

        return authority;
    }

    protected Set<Authority> authorityDTOSetToAuthoritySet(Set<AuthorityDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Authority> set1 = new LinkedHashSet<Authority>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : set ) {
            set1.add( authorityDTOToAuthority( authorityDTO ) );
        }

        return set1;
    }

    protected Role roleDTOToRole(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDTO.getId() );
        role.setName( roleDTO.getName() );
        role.setAuthorities( authorityDTOSetToAuthoritySet( roleDTO.getAuthorities() ) );

        return role;
    }

    protected Set<Role> roleDTOSetToRoleSet(Set<RoleDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleDTO roleDTO : set ) {
            set1.add( roleDTOToRole( roleDTO ) );
        }

        return set1;
    }
}
