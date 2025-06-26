package org.sportybet.formulaonebetting.controller;

import lombok.RequiredArgsConstructor;
import org.sportybet.formulaonebetting.model.response.UserDetailsResponse;
import org.sportybet.formulaonebetting.services.impl.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final CustomUserDetailsService userService;

    @GetMapping("/details")
    public ResponseEntity<UserDetailsResponse> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String userId = userDetails.getUsername();
            return ResponseEntity.ok(userService.getUserDetails(userId));
        }

        return ResponseEntity.badRequest().build();
    }
}
