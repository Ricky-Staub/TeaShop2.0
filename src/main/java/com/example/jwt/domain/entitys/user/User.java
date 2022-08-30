package com.example.jwt.domain.entitys.user;

import com.example.jwt.core.generic.ExtendedAuditEntity;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.role.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends ExtendedAuditEntity {

    @Column(name = "seeds")
    private Integer seeds = 0;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Rank rank;

    @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(UUID id, Integer seeds, String firstName, String lastName, Integer age, String email, String password, Rank rank, Set<Role> roles) {
        super(id);
        this.seeds = seeds;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.rank = rank;
        this.roles = roles;
    }

    public Integer getSeeds() {
        return seeds;
    }

    public User setSeeds(Integer seeds) {
        this.seeds = seeds;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Rank getRank() {
        return rank;
    }

    public User setRank(Rank rank) {
        this.rank = rank;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }
}
