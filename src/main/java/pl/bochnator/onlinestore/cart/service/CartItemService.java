package pl.bochnator.onlinestore.cart.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.cart.model.CartItem;
import pl.bochnator.onlinestore.cart.repository.CartItemRepository;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem create(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow();
    }

    public CartItem update(Long id, CartItem updated) {
        CartItem cartItem = findById(id);
        cartItem.setQuantity(updated.getQuantity());
        cartItem.setPrice(updated.getPrice());
        return cartItemRepository.save(cartItem);
    }

    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
