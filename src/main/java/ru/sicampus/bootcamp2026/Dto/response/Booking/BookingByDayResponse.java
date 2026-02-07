package ru.sicampus.bootcamp2026.Dto.response.Booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
public class BookingByDayResponse {

    private List<Map<String, Object>> yours;
    private List<Map<String, Object>> invited;

    public BookingByDayResponse(List<Map<String, Object>> yours,
                                List<Map<String, Object>> invited) {
        this.yours = yours;
        this.invited = invited;
    }

    public List<Map<String, Object>> getYours() {
        return yours;
    }

    public List<Map<String, Object>> getInvited() {
        return invited;
    }
}
