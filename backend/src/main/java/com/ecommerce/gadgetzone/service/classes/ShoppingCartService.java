package com.ecommerce.gadgetzone.service.classes;

import com.ecommerce.gadgetzone.dto.request.ShoppingCartRequest;
import com.ecommerce.gadgetzone.dto.response.ShoppingCartDetailsResponse;
import com.ecommerce.gadgetzone.entity.Product;
import com.ecommerce.gadgetzone.entity.ShoppingCart;
import com.ecommerce.gadgetzone.entity.ShoppingCartDetails;
import com.ecommerce.gadgetzone.entity.User;
import com.ecommerce.gadgetzone.repository.ProductRepository;
import com.ecommerce.gadgetzone.repository.ShoppingCartDetailsRepository;
import com.ecommerce.gadgetzone.repository.ShoppingCartRepository;
import com.ecommerce.gadgetzone.repository.UserRepository;
import com.ecommerce.gadgetzone.service.interfaces.IShoppingCartService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartDetailsRepository shoppingCartDetailsRepository;
    private final ProductRepository productRepository;

    
    public void addToCart(ShoppingCartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.getUserId())
        .orElseThrow(() -> new IllegalStateException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    newCart.setTotalPrice(0.0); 
                    return shoppingCartRepository.save(newCart);
                });

        Product product = productRepository.findById(cartRequest.getProductId())
        .orElseThrow(() -> new IllegalStateException("Product not found"));

        ShoppingCartDetails existingDetail = shoppingCartDetailsRepository
        .findByShoppingCartAndProduct(shoppingCart, product)
        .orElse(null);

        if (existingDetail != null) {
            existingDetail.setAmount(existingDetail.getAmount() + 1);
            shoppingCartDetailsRepository.save(existingDetail);
        } else {
            ShoppingCartDetails newDetail = new ShoppingCartDetails();
            newDetail.setAmount(1);
            newDetail.setProduct(product);
            newDetail.setShoppingCart(shoppingCart);
            shoppingCartDetailsRepository.save(newDetail);
        }

        double totalPrice = shoppingCartDetailsRepository
        .findByShoppingCart(shoppingCart)
        .stream()
        .mapToDouble(detail -> detail.getAmount() * detail.getProduct().getProductPrice())
        .sum();

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCartRepository.save(shoppingCart);
    }


    public List<ShoppingCartDetailsResponse> getCartDetails(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Shopping cart not found"));

        List<ShoppingCartDetails> cartDetails = shoppingCartDetailsRepository.findByShoppingCart(shoppingCart);

        return cartDetails.stream().map(detail -> {
            double totalPrice = detail.getAmount() * detail.getProduct().getProductPrice();
            return new ShoppingCartDetailsResponse(
                    detail.getProduct().getProductId(),
                    detail.getProduct().getProductName(),
                    detail.getProduct().getProductPicture(),
                    detail.getAmount(),
                    detail.getProduct().getProductPrice());
        }).collect(Collectors.toList());
    }


    public double getTotalCartPrice(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Shopping cart not found"));
    
        List<ShoppingCartDetails> cartDetails = shoppingCartDetailsRepository.findByShoppingCart(shoppingCart);
    
        double totalPrice = cartDetails.stream()
                .mapToDouble(detail -> detail.getAmount() * detail.getProduct().getProductPrice())
                .sum();
    
        return totalPrice;
    }


    public void updateCart(int userId, Map<Integer, Integer> quantities) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Shopping cart not found"));

        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
            int productId = entry.getKey();
            int newAmount = entry.getValue();
            Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalStateException("Product not found"));

            ShoppingCartDetails existingDetail = shoppingCartDetailsRepository
                    .findByShoppingCartAndProduct(shoppingCart, product)
                    .orElseThrow(() -> new IllegalStateException("Product not found in cart"));

            existingDetail.setAmount(newAmount);
            shoppingCartDetailsRepository.save(existingDetail);  
        }
    }


    public void removeItemFromCart(int userId, int productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));    
                
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Shopping cart not found"));
                
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        ShoppingCartDetails existingDetail = shoppingCartDetailsRepository
                .findByShoppingCartAndProduct(shoppingCart, product)
        .orElseThrow(() -> new IllegalStateException("Product not found in cart"));

        shoppingCartDetailsRepository.delete(existingDetail);
    }


}

