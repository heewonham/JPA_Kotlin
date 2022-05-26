package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member:Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems:MutableList<OrderItem> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,

    val orderDate:LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status:OrderStatus,

    ):BaseEntity(){

    // 연관관계 메서드
    fun connectMember(member:Member){
        this.member = member
        member.orders.add(this)
        return
    }

    fun addOrderItem(orderItem: OrderItem){
        orderItems.add(orderItem)
        orderItem.order = this
        return
    }

    fun connectDelivery(delivery: Delivery){
        this.delivery = delivery
        delivery.order = this
    }

    // 생성 메서드
    companion object{
        fun createOrder(member: Member, delivery: Delivery, vararg orderItem: OrderItem) : Order{
            val order = Order(member = member, delivery = delivery, status = OrderStatus.ORDER, orderDate = LocalDateTime.now())
            order.connectMember(member)
            for(oi in orderItem){
                order.addOrderItem(oi)
            }
            return order
        }
    }

    // 비즈니스 로직
    // 취소 로직
    fun cancel(){
        if(delivery.status == DeliveryStatus.COMP){
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.status = OrderStatus.CANCEL
        for(orderItem in orderItems){
            orderItem.cancel()
        }
    }

    // 조회 로직
    fun getTotalPrice() : Int{
        var totalPrice:Int = 0
        for (orderItem in orderItems){
            totalPrice += orderItem.getTotalPrice()
        }
        return totalPrice
    }


}
