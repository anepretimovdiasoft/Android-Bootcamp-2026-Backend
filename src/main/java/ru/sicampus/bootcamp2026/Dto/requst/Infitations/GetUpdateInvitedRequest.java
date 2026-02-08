package ru.sicampus.bootcamp2026.Dto.requst.Infitations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.Repository.BookingRepository;
@NoArgsConstructor
public class GetUpdateInvitedRequest {
    @NotNull
    @NotBlank
    private  String Booking_name;
    @NotNull
    private Boolean approval;

    public String getName() {
        return Booking_name;
    }

    public Boolean getApproval() {
        return approval;
    }
}
