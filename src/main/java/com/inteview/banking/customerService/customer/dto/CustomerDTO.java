package com.inteview.banking.customerService.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inteview.banking.customerService.base.dto.BaseDTO;
import com.inteview.banking.customerService.constants.GenderTypeEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO extends BaseDTO<CustomerDTO> implements Serializable {

    //@NotNull
    private String customerId;

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
    private String userRole;

    @NotNull
    private String address;

    @NotNull
    private String state;

    @NotNull
    private String country;

    public String getCustomerId() {
        return customerId;
    }

    public CustomerDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CustomerDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CustomerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public CustomerDTO setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public GenderTypeEnum getGender() {
        return gender;
    }

    public CustomerDTO setGender(GenderTypeEnum gender) {
        this.gender = gender;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public CustomerDTO setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public CustomerDTO setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public CustomerDTO setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getState() {
        return state;
    }

    public CustomerDTO setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CustomerDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDTO)) return false;
        if (!super.equals(o)) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                gender == that.gender &&
                Objects.equals(age, that.age) &&
                Objects.equals(emailId, that.emailId) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(userRole, that.userRole) &&
                Objects.equals(address, that.address) &&
                Objects.equals(state, that.state) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerId, password, firstName, middleName, lastName, gender, age, emailId, phoneNumber, userRole, address, state, country);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerId='" + customerId + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", emailId='" + emailId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userRole='" + userRole + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", id='" + id + '\'' +
                ", createdTs=" + createdTs +
                ", modifiedTs=" + modifiedTs +
                ", status=" + status +
                ", version=" + version +
                '}';
    }
}
