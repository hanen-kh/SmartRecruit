package com.SmartRecruit.Utilisateur;

import java.util.List;

public interface UtilisateurService {

   Utilisateur create (Utilisateur utilisateur);

   UtilisateurDto find (Long id);

   void delete (Long id);


   List<UtilisateurDto> getAll();

}
