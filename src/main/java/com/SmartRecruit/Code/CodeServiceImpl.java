package com.SmartRecruit.Code;

import com.SmartRecruit.Config.EmailService;
import com.SmartRecruit.Utilisateur.Utilisateur;
import com.SmartRecruit.Utilisateur.UtilisateurServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService{

    private final CodeRepository codeRepository;
    private final UtilisateurServiceImpl utilisateurService;
    private final EmailService emailService;

    @Override
    public  void codeActivation(String username){

        Utilisateur utilisateur= utilisateurService.loadUserByUsername(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Code code = new Code();
        code.setUtilisateur(utilisateur);

        // Définir les instants d'activation et d'expiration
        Instant activation = Instant.now();
        code.setActivation(activation);
        Instant expiration = activation.plus(10, MINUTES);
        code.setExpiration(expiration);

        // Générer un code de validation aléatoire à 6 chiffres
        Random random = new Random();
        String codeOTP = String.format("%06d", random.nextInt(999999));
        code.setCode(codeOTP);

        // Enregistrer l'objet Validation
        codeRepository.save(code);

        // Envoyer le mail
        String subject = "Code de Réinitialisation de mot de passe";
        String body = "Votre code : " + code.getCode() + " — il expire dans  10 minutes.";
        emailService.sendSimpleEmail(utilisateur.getEmail(), subject, body);


    }


    @Override
    public  void demandeMotDePasse(Map<String, String> parametres){

        //"email":"khmiletthanen@gmail.com"
        codeActivation(parametres.get("email"));
    }


    @Override
    public Map<String, String> validationCode(Map<String, String> parametres){

        String email = parametres.get("email");
        String code = parametres.get("code");

        Code validation = codeRepository.findByCode(code);
        Utilisateur utilisateur = validation.getUtilisateur();
        if (validation == null || Instant.now().isAfter(validation.getExpiration())) {

            Map<String, String> response = Map.of("message", "Invalid Code");
            return ResponseEntity.badRequest().body(response).getBody();
        }
        if (!utilisateur.getEmail().equals(email)) {

            Map<String, String> response = Map.of("message", "Email incorrect");
            return ResponseEntity.badRequest().body(response).getBody();
        }
        Map<String, String> response = Map.of("message", "Code correct et valide. Vous pouvez changer votre mot de passe.");
        return ResponseEntity.ok().body(response).getBody();

    }


}
