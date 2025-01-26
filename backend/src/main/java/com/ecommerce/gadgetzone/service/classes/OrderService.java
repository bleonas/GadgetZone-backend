package com.ecommerce.gadgetzone.service.classes;

import com.ecommerce.gadgetzone.dto.request.OrderDetailRequest;
import com.ecommerce.gadgetzone.dto.request.OrderRequest;
import com.ecommerce.gadgetzone.dto.response.OrderDetailsResponse;
import com.ecommerce.gadgetzone.dto.response.OrderResponse;
import com.ecommerce.gadgetzone.dto.response.AllOrdersResponse;
import com.ecommerce.gadgetzone.entity.*;
import com.ecommerce.gadgetzone.repository.OrderDetailsRepository;
import com.ecommerce.gadgetzone.repository.OrderRepository;
import com.ecommerce.gadgetzone.repository.ProductRepository;
import com.ecommerce.gadgetzone.repository.UserRepository;
import com.ecommerce.gadgetzone.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    @Transactional
    public void addOrder(OrderRequest orderRequest, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Always create a new order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(1); // Assuming 1 is the status for a new order
        order.setTotalPrice(orderRequest.getTotalPrice());
        order = orderRepository.save(order); // Save the new order to get its ID

        // Iterate through the order items to create order details
        for (OrderDetailRequest item : orderRequest.getProducts()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            // Create and save the order detail
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setProduct(product);
            orderDetails.setOrder(order);
            orderDetails.setOrderAmount(item.getAmount());
            orderDetailsRepository.save(orderDetails);
        }
    }



    public List<OrderResponse> getOrders(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        List<Order> orders = orderRepository.findAllByUser(user);

        return orders.stream().map(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId((order.getOrderId()));
            orderResponse.setTotalPrice(order.getTotalPrice());

            List<OrderDetailsResponse> products = order.getOrderDetails().stream().map(product -> {
                OrderDetailsResponse productResponse = new OrderDetailsResponse();
                productResponse.setProductName(product.getProduct().getProductName());
                productResponse.setAmount(product.getOrderAmount());
                return productResponse;
            }).collect(Collectors.toList());

            orderResponse.setOrderItems(products);
            return orderResponse;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void removeOrder(int userId, int orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));

        // Ensure the order belongs to the user
        if (!order.getUser().equals(user)) {
            throw new IllegalStateException("Order does not belong to the user");
        }

        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrder(order);
        if (!orderDetails.isEmpty()) {
            orderDetailsRepository.deleteAll(orderDetails);
        }

        orderRepository.delete(order);
    }
    
    public List<AllOrdersResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            AllOrdersResponse.OrderUser user = new AllOrdersResponse.OrderUser(
                    order.getUser().getFirstName() + " " + order.getUser().getLastName(),
                    order.getUser().getRole().name().toLowerCase()
            );

            List<AllOrdersResponse.OrderProduct> products = order.getOrderDetails().stream()
                    .map(orderDetail -> new AllOrdersResponse.OrderProduct(
                            "product" + orderDetail.getProduct().getProductId(), 
                            orderDetail.getProduct().getProductName(),
                            orderDetail.getOrderAmount()
                    ))
                    .collect(Collectors.toList());

            return new AllOrdersResponse(
                    "orderId" + order.getOrderId(), 
                    user,
                    products,
                    order.getTotalPrice()
            );
        }).collect(Collectors.toList());
    }

}

