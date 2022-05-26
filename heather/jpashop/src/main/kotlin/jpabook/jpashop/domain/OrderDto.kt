package jpabook.jpashop.domain

import java.time.LocalDateTime

data class OrderDto (
    val orderId: Long,
    val orderMemberName: String,
    val orderItem: OrderItem,
//    val orderItemName: String,
//    val orderItemPrice: Int,
//    val orderItemCount: Int,
    val orderStatus: OrderStatus,
    val orderDate: LocalDateTime
)