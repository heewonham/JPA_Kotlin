package jpabook.jpashop.service

import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.DeliveryStatus
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderDto
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.OrderSearch
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.repository.ItemRepository
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService (
    val memberRepository: MemberRepository,
    val orderRepository: OrderRepository,
    val itemRepository: ItemRepository,
){
    // 주문
    @Transactional
    fun order(memberId:Long, itemId:Long, count:Int) : Long{
        // 엔티티 조회
        var member: Member = memberRepository.findById(memberId)
        var item: Item = itemRepository.findItemById(itemId)

        // 배송정보 생성
        var delivery: Delivery = Delivery(member?.address,DeliveryStatus.READY)

        // 주문상품 생성
        var orderItem : OrderItem = OrderItem.createOrderItem(item, item.price, count)

        // 주문 생성
        val order: Order = Order.createOrder(member,delivery,orderItem)

        // 주문 저장
        orderRepository.save(order)
        return order.id

    }

    // 주문 취소
    @Transactional
    fun cancelOrder(orderId : Long) {
        // 주문 엔티티 조회
        val order : Order = orderRepository.findOrderById(orderId)

        // 주문 취소
        order.cancel()
    }

    fun findOrders(orderSearch: OrderSearch): MutableList<Order>{
        return orderRepository.findAll(orderSearch)
    }
}