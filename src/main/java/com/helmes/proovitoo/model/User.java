package com.helmes.proovitoo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "USERS")
public class User {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @NotNull(message = "name is required")
    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Lob
    @Column(name = "sectors_data", columnDefinition = "VARBINARY")
    private byte[] sectors_data;

    @Column(name = "agreed_to_terms")
    private Boolean agreed_to_terms;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public byte[] getSectors_data() {
        return sectors_data;
    }

    public void setSectors_data(byte[] sectors_data) {
        this.sectors_data = sectors_data;
    }

    public String getUserName() {
        return userName;
    }

    public Boolean getAgreed_to_terms() {
        return agreed_to_terms;
    }

    public void setAgreed_to_terms(Boolean agreed_to_terms) {
        this.agreed_to_terms = agreed_to_terms;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", first_name'" + name + '\'' +
                '}';
    }
}