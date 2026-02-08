package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatedEmployeeRequest {
    @NotNull(message = "")
    @NotBlank(message = "")
    private String name;
    @NotBlank(message = "")
    @NotNull(message = "")
    private String last_name;
    @NotBlank(message = "")
    @NotNull(message = "")
    private String father_name;
    @NotBlank(message = "")
    @NotNull(message = "")
    @Email(message = "")
    private String mail;
    private String avatar;
    @NotNull(message = "")
    @Positive(message = "")
    @Min(value = 18,message = "")
    private int age;
    @NotBlank
    @NotNull
    @Size(min=8,max = 20)
    private String password;
    @NotEmpty
    @Positive
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
