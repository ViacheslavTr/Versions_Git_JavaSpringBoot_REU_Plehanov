package com.example.springsecurityapplication.models;

import javax.persistence.*;

@Entity
@Table(name = "product_cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "product_id")
    private int productId;

    public Cart(int person_id, int product_id) {
        this.personId = person_id;
        this.productId = product_id;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int person_id) {
        this.personId = person_id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int product_id) {
        this.productId = product_id;
    }
}
