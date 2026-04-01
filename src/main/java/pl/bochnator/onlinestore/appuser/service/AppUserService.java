package pl.bochnator.onlinestore.appuser.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.repository.AppUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser create(AppUser appUser) {
        appUser.setCreatedAt(LocalDateTime.now());
        appUser.setUpdatedAt(LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser findById(Long id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow();
    }

    public AppUser update(Long id, AppUser updatedAppUser) {
        AppUser appUser = findById(id);
        appUser.setEmail(updatedAppUser.getEmail());
        appUser.setFirstName(updatedAppUser.getFirstName());
        appUser.setLastName(updatedAppUser.getLastName());
        appUser.setPhoneNumber(updatedAppUser.getPhoneNumber());
        appUser.setStatus(updatedAppUser.getStatus());
        appUser.setUpdatedAt(LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public void delete(Long id) {
        appUserRepository.deleteById(id);
    }
}
