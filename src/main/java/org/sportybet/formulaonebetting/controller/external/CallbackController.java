package org.sportybet.formulaonebetting.controller.external;

import org.sportybet.formulaonebetting.services.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external/callback/f1")
public class CallbackController {

    @Autowired
    private CallbackService callbackService;

    @PostMapping("/event-outcome")
    public ResponseEntity<String> receiveEventOutcome(@RequestBody String rawPayload,
                                                      @RequestHeader("X-API-KEY") String apiKey) {
        callbackService.handleCallback(rawPayload, apiKey);
        return ResponseEntity.ok("Callback received");
    }
}
