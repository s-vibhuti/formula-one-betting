package org.sportybet.formulaonebetting.repositories;

import org.sportybet.formulaonebetting.entities.CallbackEventPayload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallbackEventPayloadRepository extends JpaRepository<CallbackEventPayload, Long> {
}