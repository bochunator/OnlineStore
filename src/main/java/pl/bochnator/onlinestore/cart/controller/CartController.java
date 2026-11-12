package pl.bochnator.onlinestore.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.cart.model.CartResponseDto;
import pl.bochnator.onlinestore.cart.model.CreateCartDto;
import pl.bochnator.onlinestore.cart.model.UpdateCartDto;
import pl.bochnator.onlinestore.cart.service.CartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
@Tag(name = "Cart API")
public class CartController {

    private final CartService cartService;
    private final AppUserService appUserService;

    public CartController(CartService cartService, AppUserService appUserService) {
        this.cartService = cartService;
        this.appUserService = appUserService;
    }

    @PostMapping
    @Operation(summary = "Create cart")
    public CartResponseDto create(@RequestBody CreateCartDto dto) {

        //TODO: Add exception when not found user in database

        AppUser appUser = appUserService.findById(dto.getUserId());

        Cart cart = new Cart();
        cart.setAppUser(appUser);

        Cart saved = cartService.create(cart);
        return CartResponseDto.from(saved);
    }

    @GetMapping
    @Operation(summary = "Find all carts")
    public List<CartResponseDto> findAll() {
        return cartService.findAll().stream().map(CartResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find cart by id")
    public CartResponseDto findById(@PathVariable Long id) {

        Cart cart = cartService.findById(id);
        return CartResponseDto.from(cart);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart")
    public CartResponseDto update(@PathVariable Long id, @RequestBody UpdateCartDto dto) {

        // TODO: Changing cart owner doesn't make sense in real systems.
        // This endpoint should rather recalculate prices or refresh cart state.

        AppUser appUser = appUserService.findById(dto.getUserId());

        Cart cart = new Cart();
        cart.setAppUser(appUser);

        Cart updated = cartService.update(id, cart);
        return CartResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart")
    public void delete(@PathVariable Long id) {
        Cart cart = cartService.findById(id);
        cart.getAppUser().setCart(null);
        cartService.delete(id);
    }
}
