package com.ecommerce.gadgetzone.entity;


import com.ecommerce.gadgetzone.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pagesa")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @Id
    @Column(name = "pagesa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column(name = "menyra")
    private String method;

    @Column(name = "statusi")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "perdorues_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "porosi_id")
    private Order order;

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
