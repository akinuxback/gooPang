package com.aki.goosinsa.domain.entity.delivery;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "DELIVERY_SEQ_GEN", sequenceName = "DELIVERY_SEQ",  initialValue = 1, allocationSize = 1)
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public void setEntityOrder(Order order){
        this.order = order;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
