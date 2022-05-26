package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.*
import javax.persistence.*

@Entity
@Table(name = "order_item")
class OrderItem (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    var orderPrice: Int,
    var count: Int
):BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    lateinit var order: Order

    // 생성 메서드
    companion object{
        fun createOrderItem(item:Item, orderPrice: Int, count: Int) : OrderItem{
            var orderItem = OrderItem(item = item, orderPrice = orderPrice, count = count)

            item.removeStock(count)
            return orderItem
        }
    }

    // 비즈니스 로직
    // 주문 취소
    fun cancel(){
        item.addStock(count)
    }

    // 주문상품 전체 가격 조회
    fun getTotalPrice(): Int{
        return orderPrice * count
    }
}