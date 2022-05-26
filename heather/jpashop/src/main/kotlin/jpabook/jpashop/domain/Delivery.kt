package jpabook.jpashop.domain

import javax.persistence.*

@Entity
class Delivery (

    @Embedded
    var address: Address?,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus
) :BaseEntity() {
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    lateinit var order: Order
}