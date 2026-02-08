package ru.sicampus.bootcamp2026.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sicampus.bootcamp2026.Dto.response.Invited.InvitedResponse;
import ru.sicampus.bootcamp2026.Service.InvitedService;

@RestController
@RequestMapping("/api/Invited")
public class InvitedController {
    @Autowired
    private InvitedService invitedService;
    @GetMapping("/getInvited")
    public InvitedResponse getInvited(){
        InvitedResponse invitedResponse=invitedService.getInvited();
        return invitedResponse;
    }
}
