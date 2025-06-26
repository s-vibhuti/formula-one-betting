package org.sportybet.formulaonebetting.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "config")
@Data
public class Config {
    @Id
    private String configKey;

    @Column(nullable = false)
    private String configValue;

    // Getters and Setters
}
