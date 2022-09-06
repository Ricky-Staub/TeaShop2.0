package com.example.jwt.domain.entitys.user;

import com.example.jwt.core.generic.ExtendedServiceImpl;
import com.example.jwt.domain.entitys.order.OrderRepository;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.ranking.RankService;
import com.example.jwt.domain.entitys.user.dto.UserBestDTO;
import com.example.jwt.domain.entitys.user.dto.UserDTO;
import com.example.jwt.domain.entitys.user.dto.UserLostMoneyDTO;
import com.example.jwt.domain.role.Role;
import com.example.jwt.domain.role.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends ExtendedServiceImpl<User> implements UserService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RankService rankService;

    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, RankService rankService, RoleServiceImpl roleServiceImpl) {
        super(repository);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.rankService = rankService;
        this.roleServiceImpl = roleServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return ((UserRepository) super.getRepository()).findByEmail(email).map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User findByEmail(String email) {
        return findOrThrow(((UserRepository) super.getRepository()).findByEmail(email));
    }


    @Override
    public User register(User user) {
        Rank rank = rankService.findByTitle("bronze");
        Role role = roleServiceImpl.findByName("USER");
        user.setRoles(Collections.singleton(role));
        user.setRank(rank);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    public UserDetailsImpl findCurrentUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public User lockUser(UUID id, User user) {
        if (existsById(id)) {
            user.setId(id);
            user.setLocked(true);
            return save(user);
        } else {
            throw new NoSuchElementException(String.format("EA sport", id));
        }
    }

    public List<UserBestDTO> findMostOrders(){
        Optional<List<UserBestDTO>> optional = ((UserRepository) super.getRepository()).findMostOrders();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("No value present");
        }
    }


    public List<UserLostMoneyDTO> findLostMoney(){
        Optional<List<UserLostMoneyDTO>> optional = ((UserRepository) super.getRepository()).findLostMoney();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("No value present");
        }
    }

}
