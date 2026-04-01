package pl.bochnator.onlinestore.cart.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.cart.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart create(Cart cart) {
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    public Cart update(Long id, Cart updated) {
        Cart cart = findById(id);
        cart.setAppUser(updated.getAppUser());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public void delete(Long id) {
        cartRepository.deleteById(id);
    }
}
