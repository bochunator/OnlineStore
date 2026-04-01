package pl.bochnator.onlinestore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.address.service.AddressService;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.cart.model.CartItem;
import pl.bochnator.onlinestore.cart.service.CartItemService;
import pl.bochnator.onlinestore.cart.service.CartService;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.model.OrderItem;
import pl.bochnator.onlinestore.order.service.OrderItemService;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.product.model.Category;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.service.CategoryService;
import pl.bochnator.onlinestore.product.service.ProductService;
import pl.bochnator.onlinestore.security.model.Role;
import pl.bochnator.onlinestore.security.service.RoleService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final AppUserService appUserService;
    private final CartService cartService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public DataInitializer(BCryptPasswordEncoder passwordEncoder, RoleService roleService, AppUserService appUserService, CartService cartService, CategoryService categoryService, ProductService productService, CartItemService cartItemService, AddressService addressService, OrderService orderService, OrderItemService orderItemService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.appUserService = appUserService;
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.addressService = addressService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    public void run(String... args) throws Exception {

        // ROLE

        Role userRole = new Role();
        userRole.setName("USER");
        roleService.create(userRole);

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleService.create(adminRole);


        // APPUSER

        AppUser appUser = new AppUser();
        appUser.setEmail("user");
        appUser.setPassword(passwordEncoder.encode("user"));
        appUser.setFirstName("Jan");
        appUser.setLastName("Kowalski");
        appUser.setPhoneNumber("123456789");
        appUser.setRole(userRole);
        appUserService.create(appUser);

        AppUser admin = new AppUser();
        admin.setEmail("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPhoneNumber("admin");
        admin.setRole(adminRole);
        appUserService.create(admin);

        // CART

        Cart cart = new Cart();
        cart.setAppUser(appUser);
        cartService.create(cart);

        // CATEGORY

        Category motorsCategory = new Category();
        motorsCategory.setName("Motors");
        motorsCategory.setDescription("The parts you need and (really) want");
        categoryService.create(motorsCategory);

        Category motorsFirstSubCategory = new Category();
        motorsFirstSubCategory.setName("All parts and accessories");
        motorsFirstSubCategory.setDescription("Description of All parts and accessories");
        motorsFirstSubCategory.setParentCategory(motorsCategory);
        categoryService.create(motorsFirstSubCategory);

        Category motorsSecondSubCategory = new Category();
        motorsSecondSubCategory.setName("Car and truck parts");
        motorsSecondSubCategory.setDescription("Description of Car and truck parts");
        motorsSecondSubCategory.setParentCategory(motorsCategory);
        categoryService.create(motorsSecondSubCategory);

        Category electronicsCategory = new Category();
        electronicsCategory.setName("Electronics");
        electronicsCategory.setDescription("Electronics");
        categoryService.create(electronicsCategory);

        Category electronicsFirstSubCategory = new Category();
        electronicsFirstSubCategory.setName("Computers, tablets and network hardware");
        electronicsFirstSubCategory.setDescription("Description of Computers, tablets and network hardware");
        electronicsFirstSubCategory.setParentCategory(electronicsCategory);
        categoryService.create(electronicsFirstSubCategory);

        Category electronicsSecondSubCategory = new Category();
        electronicsSecondSubCategory.setName("Cameras and photo");
        electronicsSecondSubCategory.setDescription("Description of Cameras and photo");
        electronicsSecondSubCategory.setParentCategory(electronicsCategory);
        categoryService.create(electronicsSecondSubCategory);

        Category collectiblesCategory = new Category();
        collectiblesCategory.setName("Collectibles");
        collectiblesCategory.setDescription("Collect your next obssesion");
        categoryService.create(collectiblesCategory);

        Category collectiblesFirstSubCategory = new Category();
        collectiblesFirstSubCategory.setName("Sports trading cards");
        collectiblesFirstSubCategory.setDescription("Description of Sports trading cards");
        collectiblesFirstSubCategory.setParentCategory(collectiblesCategory);
        categoryService.create(collectiblesFirstSubCategory);

        Category collectiblesSecondSubCategory = new Category();
        collectiblesSecondSubCategory.setName("Collectible card games");
        collectiblesSecondSubCategory.setDescription("Description of Collectible card games");
        collectiblesSecondSubCategory.setParentCategory(collectiblesCategory);
        categoryService.create(collectiblesSecondSubCategory);

        // PRODUCT

        Product engineProduct = new Product();
        engineProduct.setName("92-09 Suzuki VS800 Intruder Engine Motor USED S503-136602");
        engineProduct.setDescription("The product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usage. This OEM part is manufactured in Japan by Suzuki and is interchangeable with the part number S503-155130. It is not a vintage part and does not have universal fitment, making it a reliable choice for those in need of a genuine Suzuki engine motor for their motorcycle.");
        engineProduct.setPrice("650.00");
        engineProduct.setSku("0123456789012");
        engineProduct.setStockQuantity(1);
        engineProduct.setCategory(motorsFirstSubCategory);
        productService.create(engineProduct);

        // CART ITEM

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(engineProduct);
        cartItem.setQuantity(1);
        cartItem.setPrice(engineProduct.getPrice());
        cartItemService.create(cartItem);

        // ADDRESS

        Address address = new Address();
        address.setStreet("Mickiewicza");
        address.setCity("Jaroslaw");
        address.setPostalCode("37-500");
        address.setCountry("Poland");
        address.setHomeNumber("48");
        address.setAppUser(appUser);
        addressService.create(address);

        // ORDER

        Order order = new Order();
        order.setAppUser(appUser);
        order.setAddress(address);
        order.setTotalPrice("2000");
        orderService.create(order);

        // ORDER ITEM

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(engineProduct);
        orderItem.setQuantity(1);
        orderItem.setPrice(engineProduct.getPrice());
        orderItem.setProductName(engineProduct.getName());
        orderItemService.create(orderItem);
    }
}
