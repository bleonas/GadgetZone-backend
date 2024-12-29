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
@Table(name = "kategoria")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {

    @Id
    @Column(name = "kategoria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kategoria_id;

    @Column(name = "emri_kategori")
    private String categoryName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "category", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Product> products;

    public int getKategoria_id() {
        return kategoria_id;
    }

    public void setKategoria_id(int kategoria_id) {
        this.kategoria_id = kategoria_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
