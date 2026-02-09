package ru.sicampus.bootcamp2026.Dto.response.Invited;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
public class InvitedResponse {
    private List<Map<String,String>> you;//ты//
    private List<Map<String,String>> yours ;//тебе//;
    private int totalPages;
    private long totalElements;
    public void setResult(List<Map<String, String>> result) {
        this.you = result;
    }

    public void setResult1(List<Map<String, String>> result1) {
        this.yours = result1;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
