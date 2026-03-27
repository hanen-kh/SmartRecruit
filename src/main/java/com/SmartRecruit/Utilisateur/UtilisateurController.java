package com.SmartRecruit.Utilisateur;


import com.SmartRecruit.Code.CodeServiceImpl;
import com.SmartRecruit.Jwt.Authentification;
import com.SmartRecruit.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users") //à expliquer
@CrossOrigin (origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UtilisateurController {

private final UtilisateurServiceImpl utilisateurService;
    private final CodeServiceImpl codeService;
    private  final JwtService jwtService;

@PostMapping
    public Utilisateur create (@RequestBody Utilisateur utilisateur){
     return utilisateurService.create(utilisateur);
}


@GetMapping("/{id}")
    public UtilisateurDto get (@PathVariable Long id){
     return  utilisateurService.find(id);
}


@DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){
      utilisateurService.delete(id);
}


@GetMapping
    public List<UtilisateurDto> getAll(){
     return utilisateurService.getAll();
}

@PostMapping(path="/resetPassword")
    public ResponseEntity<String> requestNewPassword(@RequestBody Map<String, String> parametres){
        codeService.demandeMotDePasse(parametres);
        return ResponseEntity.status(HttpStatus.OK).body("Your code is sent by email");
    }

    @PostMapping("/validate-code")
    public ResponseEntity<Map<String, String>> validateCode(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(codeService.validationCode(request));
    }


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Authentification authentification) {


            Utilisateur utilisateur = utilisateurService.loadUserByUsername(authentification.username());

            return jwtService.generate(utilisateur.getUsername());


    }



}
