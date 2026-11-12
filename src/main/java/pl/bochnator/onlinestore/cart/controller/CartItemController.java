package pl.bochnator.onlinestore.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CartItem API")
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
    @Operation(summary = "Create cart item")
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
    @Operation(summary = "Find all cart items")
    public List<CartItemResponseDto> findAll() {
        return cartItemService.findAll().stream().map(CartItemResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find cart item by id")
    public CartItemResponseDto findById(@PathVariable Long id) {

        CartItem cartItem = cartItemService.findById(id);
        return CartItemResponseDto.from(cartItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart item")
    public CartItemResponseDto update(@PathVariable Long id, @RequestBody UpdateCartItemDto dto) {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setPrice(dto.getPrice());

        CartItem updated = cartItemService.update(id, cartItem);
        return CartItemResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart item")
    public void delete(@PathVariable Long id) {
        cartItemService.delete(id);
    }
}
