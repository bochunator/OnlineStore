package pl.bochnator.onlinestore.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;
import pl.bochnator.onlinestore.security.model.Role;

@Service
public class AuthService {

    private final AppUserService appUserService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserService appUserService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(AppUser user) {

        Role role = roleService.findByName("USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);

        return appUserService.create(user);
    }

    public AppUser login(String email) {
        return appUserService.findByEmail(email);
    }
}
