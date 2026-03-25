package com.SmartRecruit.Utilisateur;

import com.SmartRecruit.Code.CodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService{

    private  final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
   public Utilisateur create (Utilisateur utilisateur) {

        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()){
            throw new RuntimeException("un utilisateur deja existe avec cet email");
        }

        String hashedPassword= passwordEncoder.encode(utilisateur.getMotdepasse());

        utilisateur.setMotdepasse(hashedPassword);

       return utilisateurRepository.save(utilisateur);

    }


    @Override
    public UtilisateurDto find (Long id) {
       Utilisateur utilisateur=utilisateurRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Aucun utilisateur avec cet ID:" + id));

       return this.UserToDto(utilisateur);

    }

    private UtilisateurDto UserToDto (Utilisateur utilisateur) {
        UtilisateurDto dto = new UtilisateurDto();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setEmail(utilisateur.getEmail());

        return dto;
    }

    @Override
    public void delete (Long id){

         if ( utilisateurRepository.findById(id).isEmpty() ){
             throw new RuntimeException("aucun utilisateur avec cet id");
         }
         utilisateurRepository.deleteById(id);

    }

    @Override
    public List<UtilisateurDto> getAll (){

        return utilisateurRepository.findAll()
                .stream()
                .map(this::UserToDto)
                .toList();
    }


    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("No user matches this ID"));
    }


}
