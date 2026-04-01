package pl.bochnator.onlinestore.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.address.model.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByAppUserId(Long appUserId);
}
