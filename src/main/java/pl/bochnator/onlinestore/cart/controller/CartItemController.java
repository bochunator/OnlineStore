package pl.bochnator.onlinestore.cart.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.cart.model.*;
import pl.bochnator.onlinestore.cart.service.CartItemService;
import pl.bochnator.onlinestore.cart.service.CartService;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ProductService productService;

    public CartItemController(CartItemService cartItemService, CartService cartService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public CartItemResponseDto create(@RequestBody CreateCartItemDto dto) {

        // TODO: If seller is going to change price then in cart should also changed price
        // TODO: check if there is already product in cart and if is, then just increase quantity
        Cart cart = cartService.findById(dto.getCartId());
        Product product = productService.findById(dto.getProductId());

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);

        // TODO: check quantity in database, if there is less then client cannot add more to cart
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setPrice(product.getPrice());

        CartItem saved = cartItemService.create(cartItem);
        return CartItemResponseDto.from(saved);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CartItemResponseDto> findAll() {
        return cartItemService.findAll().stream().map(CartItemResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public CartItemResponseDto findById(@PathVariable Long id) {

        CartItem cartItem = cartItemService.findById(id);
        return CartItemResponseDto.from(cartItem);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public CartItemResponseDto update(@PathVariable Long id, @RequestBody UpdateCartItemDto dto) {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setPrice(dto.getPrice());

        CartItem updated = cartItemService.update(id, cartItem);
        return CartItemResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public void delete(@PathVariable Long id) {
        cartItemService.delete(id);
    }
}
