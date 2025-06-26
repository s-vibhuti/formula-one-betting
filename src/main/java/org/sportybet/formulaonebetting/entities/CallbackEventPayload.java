package org.sportybet.formulaonebetting.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "callback_event_payloads")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallbackEventPayload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String rawPayload;

    private Instant receivedAt;
}