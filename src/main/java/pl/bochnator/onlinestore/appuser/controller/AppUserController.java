package pl.bochnator.onlinestore.appuser.controller;

import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.model.AppUserResponseDto;
import pl.bochnator.onlinestore.appuser.model.CreateAppUserDto;
import pl.bochnator.onlinestore.appuser.model.UpdateAppUserDto;
import pl.bochnator.onlinestore.appuser.service.AppUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app-users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    public AppUserResponseDto create(@RequestBody CreateAppUserDto dto) {
        AppUser appUser = new AppUser();
        appUser.setEmail(dto.getEmail());
        appUser.setPassword(dto.getPassword());
        appUser.setFirstName(dto.getFirstName());
        appUser.setLastName(dto.getLastName());
        appUser.setPhoneNumber(dto.getPhoneNumber());

        AppUser saved = appUserService.create(appUser);
        return AppUserResponseDto.from(saved);
    }

    @GetMapping
    public List<AppUserResponseDto> findAll() {
        return appUserService.findAll().stream().map(AppUserResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AppUserResponseDto findById(@PathVariable Long id) {
        AppUser user = appUserService.findById(id);
        return AppUserResponseDto.from(user);
    }

    @GetMapping("/email")
    public AppUserResponseDto findByEmail(@RequestParam String email) {
        AppUser user = appUserService.findByEmail(email);
        return AppUserResponseDto.from(user);
    }


    @PutMapping("/{id}")
    public AppUserResponseDto update(@PathVariable Long id, @RequestBody UpdateAppUserDto dto) {
        AppUser appUser = new AppUser();
        appUser.setFirstName(dto.getFirstName());
        appUser.setLastName(dto.getLastName());
        appUser.setPhoneNumber(dto.getPhoneNumber());
        appUser.setStatus(dto.getStatus());

        AppUser updated = appUserService.update(id, appUser);
        return AppUserResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // TODO: Make exception or change logic: You cannot delete user, you have to firstly delete cart
        appUserService.delete(id);
    }
}
