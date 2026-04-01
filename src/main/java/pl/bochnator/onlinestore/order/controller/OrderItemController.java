package pl.bochnator.onlinestore.order.controller;

import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.order.model.*;
import pl.bochnator.onlinestore.order.service.OrderItemService;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService service;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService service, OrderService orderService, ProductService productService) {
        this.service = service;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping
    public OrderItemResponseDto create(@RequestBody CreateOrderItemDto dto) {

        Order order = orderService.findById(dto.getOrderId());
        Product product = productService.findById(dto.getProductId());

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setProductName(dto.getProductName());

        OrderItem saved = service.create(item);
        return OrderItemResponseDto.from(saved);
    }

    @GetMapping
    public List<OrderItemResponseDto> findAll() {
        return service.findAll().stream().map(OrderItemResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderItemResponseDto findById(@PathVariable Long id) {
        OrderItem orderItem = service.findById(id);
        return OrderItemResponseDto.from(orderItem);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponseDto> findByOrder(@PathVariable Long orderId) {
        return service.findByOrderId(orderId).stream().map(OrderItemResponseDto::from).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public OrderItemResponseDto update(@PathVariable Long id, @RequestBody UpdateOrderItemDto dto) {

        Product product = productService.findById(dto.getProductId());

        OrderItem item = new OrderItem();
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setProductName(dto.getProductName());
        item.setProduct(product);

        OrderItem updated = service.update(id, item);
        return OrderItemResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
