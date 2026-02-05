package ru.sicampus.bootcamp2026.Dto.requst;

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
    private long avatar;
    @NotNull(message = "")
    @Positive(message = "")
    @Min(value = 18,message = "")
    private int age;
    @NotBlank
    @NotNull
    @Size(min=8,max = 20)
    private String password;

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

    public long getAvatar() {
        return avatar;
    }

    public void setAvatar(long avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }
}
