package com.ecommerce.gadgetzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "detaje_magazina")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WarehouseDetails {

    @Id
    @Column(name = "detaje_magazina_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int whDetailsId;

    @Column(name = "sasia")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "produkt_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "magazina_id")
    private Warehouse warehouse;


    public int getWhDetailsId() {
        return whDetailsId;
    }

    public void setWhDetailsId(int whDetailsId) {
        this.whDetailsId = whDetailsId;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
