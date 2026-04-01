package pl.bochnator.onlinestore.security.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.security.model.Role;
import pl.bochnator.onlinestore.security.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }
}
