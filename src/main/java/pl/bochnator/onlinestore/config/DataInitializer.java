package pl.bochnator.onlinestore.config;

import org.springframework.boot.CommandLineRunner;
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

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserService appUserService;
    private final CartService cartService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public DataInitializer(AppUserService appUserService, CartService cartService, CategoryService categoryService, ProductService productService, CartItemService cartItemService, AddressService addressService, OrderService orderService, OrderItemService orderItemService) {
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

        // APPUSER

        AppUser appUser = new AppUser();
        appUser.setEmail("user");
        appUser.setPassword("user");
        appUser.setFirstName("Jan");
        appUser.setLastName("Kowalski");
        appUser.setPhoneNumber("123456789");
        appUserService.create(appUser);

        AppUser admin = new AppUser();
        admin.setEmail("admin");
        admin.setPassword("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPhoneNumber("admin");
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
        engineProduct.setDescription("The product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usageThe product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usageThe product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usageThe product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usageThe product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usageThe product is a used Suzuki VS800 Intruder engine motor, specifically designed for street motorcycles. It is a black engine motor assembly with 40,000 miles of usage. This OEM part is manufactured in Japan by Suzuki and is interchangeable with the part number S503-155130. It is not a vintage part and does not have universal fitment, making it a reliable choice for those in need of a genuine Suzuki engine motor for their motorcycle.");
        engineProduct.setPrice(new BigDecimal("650.00"));
        engineProduct.setSku("0123456789012");
        engineProduct.setStockQuantity(10);
        engineProduct.setCategory(motorsFirstSubCategory);
        productService.create(engineProduct);

        Product transmissionProduct = new Product();
        transmissionProduct.setName("Automatic Transmission Assembly Jegs 603038");
        transmissionProduct.setDescription("4R70W Performance Transmission, 1998-2004 Ford 2WD Trucks, 4-Speed Automatic, Lockup, Case Fill, Dyno Tested|JEGS 4R70W Performance Transmission for 1998-2004 Ford 2WD Trucks");
        transmissionProduct.setPrice(new BigDecimal("2199.98"));
        transmissionProduct.setSku("0123456789612");
        transmissionProduct.setStockQuantity(10);
        transmissionProduct.setCategory(motorsFirstSubCategory);
        productService.create(transmissionProduct);

        Product brakePads = new Product();
        brakePads.setName("Brembo Ceramic Brake Pads Set Front");
        brakePads.setDescription("High-performance ceramic brake pads designed for reduced dust and noise. מתאים for most European vehicles. Ensures smooth and safe braking.");
        brakePads.setPrice(new BigDecimal("89.99"));
        brakePads.setSku("BRM-001");
        brakePads.setStockQuantity(50);
        brakePads.setCategory(motorsSecondSubCategory);
        productService.create(brakePads);

        Product battery = new Product();
        battery.setName("Bosch S5 Car Battery 12V 77Ah");
        battery.setDescription("Reliable and long-lasting car battery suitable for modern vehicles with high energy demands. Maintenance-free and durable.");
        battery.setPrice(new BigDecimal("149.99"));
        battery.setSku("BOS-777");
        battery.setStockQuantity(30);
        battery.setCategory(motorsSecondSubCategory);
        productService.create(battery);

        Product headlights = new Product();
        headlights.setName("Philips LED Headlight Bulbs H7 Ultinon");
        headlights.setDescription("Bright white LED headlights with long lifespan and improved night visibility.");
        headlights.setPrice(new BigDecimal("59.99"));
        headlights.setSku("LED-H7-999");
        headlights.setStockQuantity(100);
        headlights.setCategory(motorsSecondSubCategory);
        productService.create(headlights);

        Product laptop = new Product();
        laptop.setName("Lenovo IdeaPad 5 15ITL05 i5/16GB/512GB SSD");
        laptop.setDescription("Powerful everyday laptop with Intel i5 processor, 16GB RAM and fast SSD storage. Perfect for work and study.");
        laptop.setPrice(new BigDecimal("799.99"));
        laptop.setSku("LNV-555");
        laptop.setStockQuantity(20);
        laptop.setCategory(electronicsFirstSubCategory);
        productService.create(laptop);

        Product keyboard = new Product();
        keyboard.setName("Keychron K6 Wireless Mechanical Keyboard RGB");
        keyboard.setDescription("Compact 65% mechanical keyboard with hot-swappable switches and RGB lighting. Works with Mac and Windows.");
        keyboard.setPrice(new BigDecimal("129.99"));
        keyboard.setSku("KEY-K6");
        keyboard.setStockQuantity(40);
        keyboard.setCategory(electronicsFirstSubCategory);
        productService.create(keyboard);

        Product camera = new Product();
        camera.setName("Canon EOS 250D DSLR Camera + 18-55mm Lens");
        camera.setDescription("Lightweight DSLR camera with 24.1MP sensor, 4K video and Wi-Fi connectivity. Perfect for beginners.");
        camera.setPrice(new BigDecimal("599.99"));
        camera.setSku("CAN-250D");
        camera.setStockQuantity(15);
        camera.setCategory(electronicsSecondSubCategory);
        productService.create(camera);

        Product pokemonCards = new Product();
        pokemonCards.setName("Pokemon TCG Scarlet & Violet Booster Pack");
        pokemonCards.setDescription("Original Pokemon Trading Card Game booster pack. Expand your collection and find rare cards.");
        pokemonCards.setPrice(new BigDecimal("4.99"));
        pokemonCards.setSku("PKM-001");
        pokemonCards.setStockQuantity(200);
        pokemonCards.setCategory(collectiblesSecondSubCategory);
        productService.create(pokemonCards);

        Product footballCard = new Product();
        footballCard.setName("Panini FIFA World Cup Trading Card Pack");
        footballCard.setDescription("Official FIFA World Cup trading cards by Panini. Collect your favorite players.");
        footballCard.setPrice(new BigDecimal("3.49"));
        footballCard.setSku("PAN-2022");
        footballCard.setStockQuantity(150);
        footballCard.setCategory(collectiblesFirstSubCategory);
        productService.create(footballCard);

        Product mtg = new Product();
        mtg.setName("Magic: The Gathering Starter Kit 2023");
        mtg.setDescription("Perfect starter set for new players. Includes two ready-to-play decks and guide.");
        mtg.setPrice(new BigDecimal("19.99"));
        mtg.setSku("MTG-START");
        mtg.setStockQuantity(60);
        mtg.setCategory(collectiblesSecondSubCategory);
        productService.create(mtg);

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
        order.setTotalPrice(new BigDecimal("2000"));
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
