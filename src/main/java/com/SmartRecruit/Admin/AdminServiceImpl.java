package com.SmartRecruit.Admin;

import com.SmartRecruit.Utilisateur.Role;
import com.SmartRecruit.Utilisateur.Utilisateur;
import com.SmartRecruit.Utilisateur.UtilisateurDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDto create (Admin admin){
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()){
            throw new RuntimeException("un utilisateur deja existe avec cet email");
        }

        String hashedPassword= passwordEncoder.encode(admin.getMotdepasse());

        admin.setMotdepasse(hashedPassword);
        admin.setRole(Role.ADMIN);
        adminRepository.save(admin);
        AdminDto adminDto = new AdminDto();
        adminDto= this.AdminToDto(admin);
        return  adminDto;
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
