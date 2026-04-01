package pl.bochnator.onlinestore.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.security.model.AuthResponseDto;
import pl.bochnator.onlinestore.security.model.LoginDto;
import pl.bochnator.onlinestore.security.model.RegisterDto;
import pl.bochnator.onlinestore.security.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegisterDto dto) {

        AppUser appUser = new AppUser();
        appUser.setEmail(dto.getEmail());
        appUser.setPassword(dto.getPassword());
        appUser.setFirstName(dto.getFirstName());
        appUser.setLastName(dto.getLastName());

        AppUser saved = authService.register(appUser);

        AuthResponseDto response = new AuthResponseDto();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole().getName());

        return response;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginDto dto) {

        AppUser appUser = authService.login(dto.getEmail());

        AuthResponseDto response = new AuthResponseDto();
        response.setId(appUser.getId());
        response.setEmail(appUser.getEmail());
        response.setRole(appUser.getRole().getName());

        return response;
    }
}
