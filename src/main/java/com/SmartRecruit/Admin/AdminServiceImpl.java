package com.SmartRecruit.Admin;

import com.SmartRecruit.Utilisateur.Role;
import com.SmartRecruit.Utilisateur.Utilisateur;
import com.SmartRecruit.Utilisateur.UtilisateurDto;
import com.SmartRecruit.Utilisateur.UtilisateurServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurServiceImpl utilisateurService;

    @Override
    public AdminDto create(Admin admin) {

        admin.setRole(Role.ADMIN);

        // 🔥 Réutilisation logique commune (email + password hash)
        Admin savedAdmin = (Admin) utilisateurService.create(admin);

        return this.AdminToDto(savedAdmin);
    }


    private AdminDto AdminToDto (Admin admin) {
        AdminDto dto = new AdminDto() ;
        dto.setId(admin.getId());
        dto.setNom(admin.getNom());
        dto.setPrenom(admin.getPrenom());
        dto.setEmail(admin.getEmail());
        dto.setRole(String.valueOf(admin.getRole()));

        return dto;
    }
}
