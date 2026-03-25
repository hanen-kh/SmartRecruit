package com.SmartRecruit.Code;

import com.SmartRecruit.Utilisateur.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Instant expiration;
    private Instant activation;
    private String code;
    @ManyToOne(cascade= CascadeType.ALL)
    private Utilisateur utilisateur;
}
