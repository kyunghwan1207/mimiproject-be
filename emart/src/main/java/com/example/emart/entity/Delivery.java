package com.example.emart.entity;

import com.example.emart.entity.enums.DELIVERY_STATUS;
import com.example.emart.entity.enums.ORDER_STATUS;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
@Getter @Setter
@NoArgsConstructor
public class Delivery extends BaseTime {
    @Id @GeneratedValue
    private Long id;
    private String address;

    @Enumerated(EnumType.STRING)
    private DELIVERY_STATUS deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
}
