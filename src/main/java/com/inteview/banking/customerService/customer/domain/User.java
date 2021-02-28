package com.inteview.banking.customerService.customer.domain;

import com.inteview.banking.customerService.base.domain.BaseDomainObject;
import com.inteview.banking.customerService.constants.GenderTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userRole", discriminatorType = DiscriminatorType.STRING)
public class User extends BaseDomainObject<User> {

  //  @Size(min = 8, max = 8)
    @NotNull
    @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GenderTypeEnum gender;

    private Integer age;

    @NotNull
    private String emailId;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String address;

    @NotNull
    private String state;

    @NotNull
    private String country;

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public User setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public GenderTypeEnum getGender() {
        return gender;
    }

    public User setGender(GenderTypeEnum gender) {
        this.gender = gender;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public User setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getState() {
        return state;
    }

    public User setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }
}
