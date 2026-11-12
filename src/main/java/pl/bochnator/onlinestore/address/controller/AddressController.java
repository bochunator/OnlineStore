package pl.bochnator.onlinestore.address.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.address.model.AddressResponseDto;
import pl.bochnator.onlinestore.address.model.CreateAddressDto;
import pl.bochnator.onlinestore.address.model.UpdateAddressDto;
import pl.bochnator.onlinestore.address.service.AddressService;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("addresses")
@Tag(name = "Address API")
public class AddressController {

    private final AddressService addressService;
    private final AppUserService appUserService;

    public AddressController(AddressService addressService, AppUserService appUserService) {
        this.addressService = addressService;
        this.appUserService = appUserService;
    }

    @PostMapping
    @Operation(summary = "Create address")
    public AddressResponseDto create(@RequestBody CreateAddressDto dto) {

        // TODO: Consider changing relation to many-to-many (AppUser <-> Address),
        // because multiple users can share the same address and one user can have multiple addresses.

        AppUser user = appUserService.findById(dto.getAppUserId());

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setHomeNumber(dto.getHomeNumber());
        address.setAppUser(user);

        Address saved = addressService.create(address);
        return AddressResponseDto.from(saved);
    }

    @GetMapping
    @Operation(summary = "Get all addresses")
    public List<AddressResponseDto> findAll() {
        return addressService.findAll().stream().map(AddressResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by id")
    public AddressResponseDto findById(@PathVariable Long id) {

        Address address = addressService.findById(id);
        return AddressResponseDto.from(address);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update address")
    public AddressResponseDto update(
            @PathVariable Long id,
            @RequestBody UpdateAddressDto dto) {

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setHomeNumber(dto.getHomeNumber());

        Address updated = addressService.update(id, address);
        return AddressResponseDto.from(updated);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get address by AppUser id")
    public List<AddressResponseDto> findByUser(@PathVariable Long userId) {
        return addressService.findByUserId(userId).stream().map(AddressResponseDto::from).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete address")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
