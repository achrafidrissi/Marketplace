package com.maarketplace.controller;

import com.maarketplace.DTO.Cart.CartLineItemRequest;
import com.maarketplace.DTO.Cart.CartLineItemResponse;
import com.maarketplace.DTO.Cart.CartResponse;
import com.maarketplace.exception.NotFoundException;
import com.maarketplace.model.Cart;
import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
import com.maarketplace.model.User;
import com.maarketplace.service.CartService;
import com.maarketplace.service.ProductService;
import com.maarketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Manage cart operations")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @Operation(summary = "Get user's current cart")
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getUserCart(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCarts().stream().findFirst() // You may improve this logic to get the most recent cart
                .orElseThrow(() -> new NotFoundException("No cart found for user"));

        Float totalPrice = cartService.calculateCartPrice(cart);
        List<CartLineItemResponse> items = cart.getCartLineItems().stream()
                .map(CartLineItemResponse::new)
                .toList();

        CartResponse response = new CartResponse(items, totalPrice);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a product to the cart")
    @PostMapping("/{userId}/items")
    public ResponseEntity<CartLineItemResponse> addItemToCart(@PathVariable Long userId,
                                                              @Valid @RequestBody CartLineItemRequest request) {
        User user = userService.getUser(userId);
        Product product = productService.getProduct(request.getProductId());
        Cart cart = user.getCarts().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("No cart found for user"));

        CartLineItem lineItem = cartService.makeCartLineItem(cart, product, request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CartLineItemResponse(lineItem));
    }

    @Operation(summary = "Update a line item quantity")
    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long userId,
                                               @PathVariable Long itemId,
                                               @RequestParam Integer quantity) {
        User user = userService.getUser(userId);
        Cart cart = user.getCarts().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("No cart found for user"));

        cartService.updateCartLineItem(cart, itemId, quantity);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a line item from the cart")
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long userId,
                                               @PathVariable Long itemId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCarts().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("No cart found for user"));

        cartService.deleteCartLineItem(cart, itemId);
        return ResponseEntity.noContent().build();
    }
}
