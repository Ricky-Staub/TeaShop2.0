package com.example.TeaShop2.domain.entitys.user.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;
import com.example.TeaShop2.domain.entitys.ranking.Rank;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.UUID;

public class UserDTO extends ExtendedDTO {

    @Min(value = 0)
    private Integer seeds;
    private Rank rank;

    private String firstName;

    private String lastName;

    private Integer age;

    @Email
    private String email;

//    private Set<RoleDTO> roles;

    public UserDTO() {
    }

    public UserDTO(UUID id, Integer seeds, Rank rank, String firstName, String lastName, Integer age, String email) {
        super(id);
        this.seeds = seeds;
        this.rank = rank;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Integer getSeeds() {
        return seeds;
    }

    public UserDTO setSeeds(Integer seeds) {
        this.seeds = seeds;
        return this;
    }

    public Rank getRank() {
        return rank;
    }

    public UserDTO setRank(Rank rank) {
        this.rank = rank;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserDTO setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
