package com.SmartRecruit.Utilisateur;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance (strategy = InheritanceType.JOINED)
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motdepasse;

    @Enumerated (EnumType.STRING)
    private Role role;


}
