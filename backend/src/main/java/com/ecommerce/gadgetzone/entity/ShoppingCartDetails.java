package com.ecommerce.gadgetzone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "detaje_shporta")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShoppingCartDetails {

    @Id
    @Column(name = "detaje_shporta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoppingCartDetailsId;

    @Column(name = "sasia")
    private int amount;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "produkt_id")
    private Product product;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "shporta_id")
    private ShoppingCart shoppingCart;

    public int getShoppingCartDetailsId() {
        return shoppingCartDetailsId;
    }

    public void setShoppingCartDetailsId(int shoppingCartDetailsId) {
        this.shoppingCartDetailsId = shoppingCartDetailsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
