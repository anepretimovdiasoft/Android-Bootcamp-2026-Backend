package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatedEmployeeRequest {
    @NotNull(message = "The name cannot be empty.")
    @NotBlank(message = "the name cannot be an empty string or contain spaces")
    private String name;
    @NotBlank(message = "a last name cannot be an empty string or contain spaces")
    @NotNull(message = "The last name cannot be empty.")
    private String last_name;
    @NotBlank(message = "The patronymic cannot be an empty string or contain spaces.")
    @NotNull(message = "The patronymic cannot be empty")
    private String father_name;
    @NotBlank(message = "mail is not entered")
    @NotNull(message = "mail cannot be empty")
    @Email(message = "mail does not match the email format")
    private String mail;
    private String avatar;
    @NotNull(message = "")
    @Positive(message = "")
    @Min(value = 18,message = "the age cannot be less than 18")
    private int age;
    @NotBlank
    @NotNull
    @Size(min=8,max = 20)
    private String password;
    @Positive
    @Min(value = 4)
    private Long code;

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public int getAge() {
        return age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public Long getCode() {
        return code;
    }
}
