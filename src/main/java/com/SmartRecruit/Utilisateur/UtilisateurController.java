package com.SmartRecruit.Utilisateur;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") //à expliquer
@CrossOrigin (origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UtilisateurController {

private final UtilisateurServiceImpl utilisateurService;


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

}
