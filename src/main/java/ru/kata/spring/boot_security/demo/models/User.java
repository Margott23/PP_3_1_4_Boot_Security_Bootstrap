package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, int age, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
        this.password = password;
    }

    public User(String name, String surname, int age, String login, String password, List<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        if (roles != null) {
            if (!roles.isEmpty() && roles.get(0).getOnlyName().equals("none")) {
                return "none";
            }
            String str = roles.stream().map(Role::getName).map(s -> s.replace("ROLE_", "")).toList().toString();
            return str.substring(1, str.length() - 1);
        }
        return "Null";
    }

    public List<Role> getRoleList() {
        return roles;
    }

    public void setRoles(String role) {
        if (role.contains(",")) {
            String[] rolesList = role.split(",");
            for (String roleStr : rolesList) {
                roles.add(new Role(roleStr.trim()));
            }
        } else {
            roles.add(new Role(role));
        }
    }

    public void setRoleList(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [" +
                "id: " + id +
                ", name: " + name + " " + surname +
                ", " + age +
                ", roles: " + roles.stream().map(r -> r.getName()).toList() +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            if (role.getName().isEmpty()) {
                authorities = AuthorityUtils.NO_AUTHORITIES;
            } else {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
