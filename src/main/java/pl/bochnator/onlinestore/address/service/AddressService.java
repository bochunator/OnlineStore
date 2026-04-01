package pl.bochnator.onlinestore.address.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.address.repository.AddressRepository;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow();
    }

    public List<Address> findByUserId(Long userId) {
        return addressRepository.findByAppUserId(userId);
    }

    public Address update(Long id, Address updated) {
        Address address = findById(id);
        address.setStreet(updated.getStreet());
        address.setCity(updated.getCity());
        address.setPostalCode(updated.getPostalCode());
        address.setCountry(updated.getCountry());
        address.setHomeNumber(updated.getHomeNumber());
        return addressRepository.save(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
