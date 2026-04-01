package pl.bochnator.onlinestore.security.model;

import jakarta.persistence.*;
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.appuser.model.AppUser;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role")
    private List<AppUser> appUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
