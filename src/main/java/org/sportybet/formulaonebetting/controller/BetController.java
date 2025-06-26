package org.sportybet.formulaonebetting.controller;

import org.sportybet.formulaonebetting.model.request.PlaceBetRequest;
import org.sportybet.formulaonebetting.model.response.PlaceBetResponse;
import org.sportybet.formulaonebetting.services.BettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/f1/bets")
public class BetController {

    @Autowired
    private BettingService bettingService;

    // fetch user details from jwt token
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername(); // or getUserId() if you extend UserDetails
        }

        throw new RuntimeException("User is not authenticated");
    }

    @PostMapping("/place-bet")
    public ResponseEntity<List<PlaceBetResponse>> placeBet(@RequestBody PlaceBetRequest request) {
        return ResponseEntity.ok(bettingService.placeBet(request, getCurrentUserId()));
    }

}
