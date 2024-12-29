package com.ecommerce.gadgetzone.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "magazina")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Warehouse {

    @Id
    @Column(name = "magazina_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    @Column(name = "emri_magazina")
    private String warehouseName;

    @Column(name = "adresa")
    private String address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "warehouse", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<WarehouseDetails> warehouseDetails;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<WarehouseDetails> getWarehouseDetails() {
        return warehouseDetails;
    }

    public void setWarehouseDetails(List<WarehouseDetails> warehouseDetails) {
        this.warehouseDetails = warehouseDetails;
    }
}
