package org.sportybet.formulaonebetting.controller;

import org.sportybet.formulaonebetting.model.response.SessionResponseDto;
import org.sportybet.formulaonebetting.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/f1/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<SessionResponseDto>> getSessions(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String sessionType,
            @RequestParam(required = false) Integer year
    ) {
        return ResponseEntity.ok(sessionService.getSessions(country, sessionType, year));
    }
}
