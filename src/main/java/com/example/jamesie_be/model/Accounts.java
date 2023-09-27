package com.example.jamesie_be.model;
import javax.persistence.*;


@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false,unique = true, columnDefinition = "TEXT")
    private String password;

    private Integer confirmationCode;

    private boolean enable = false;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Accounts() {
    }

    public Accounts(Long id, String name, String password, Integer confirmationCode, boolean enable, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.confirmationCode = confirmationCode;
        this.enable = enable;
        this.role = role;

    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Accounts(String name, String password, Integer confirmationCode, Role role) {
        this.name = name;
        this.password = password;
        this.confirmationCode = confirmationCode;
        this.role = role;
    }

    public Accounts(Long id, String name, String password, Integer confirmationCode, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.confirmationCode = confirmationCode;
        this.role = role;
    }

    public Accounts(Long id) {
        this.id = id;
    }

    public Accounts(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Accounts(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Accounts(Integer confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Accounts(String name, String password, Integer confirmationCode) {
        this.name = name;
        this.password = password;
        this.confirmationCode = confirmationCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(Integer confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
