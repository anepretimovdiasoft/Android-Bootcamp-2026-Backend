package ru.sicampus.bootcamp2026.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetUpdateInvitedRequest;
import ru.sicampus.bootcamp2026.Dto.response.Invited.InvitedResponse;
import ru.sicampus.bootcamp2026.Service.InvitedService;

@RestController
@RequestMapping("/api/Invited")
public class InvitedController {
    @Autowired
    private InvitedService invitedService;
    @GetMapping("/getInvited")
    public ResponseEntity<?> getInvited(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        InvitedResponse response = invitedService.getInvited(page, size);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/updateIn")
    public  void updateInvited(GetUpdateInvitedRequest dto){
        invitedService.updateInvited(dto);
    }
}
