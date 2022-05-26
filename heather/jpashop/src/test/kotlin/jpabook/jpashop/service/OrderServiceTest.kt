package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import org.assertj.core.api.Assertions

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.jupiter.api.DisplayName

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@SpringBootTest
@Transactional
@RunWith(SpringRunner::class)
class OrderServiceTest(){

    @PersistenceContext
    lateinit var em: EntityManager

    @Autowired
    lateinit var orderService : OrderService
    @Autowired
    lateinit var orderRepository : OrderRepository

    @Test
    @DisplayName("상품 주문 테스트")
    fun orderItemTest(){

        // given
        val member:Member = createMember()
        val item : Item = createBook("kotlin",10000,10)
        var orderCount = 2

        // when
        var orderId :Long = orderService.order(member.id, item.id, orderCount)

        // then
        var getOrder : Order = orderRepository.findOrderById(orderId)

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.status)
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.orderItems.size)
        assertEquals("주문 가격은 가격 * 수량이다", 10000*2 , getOrder.getTotalPrice())
        assertEquals("주문 수량만큼 재고가 줄어야 한다", 8, item.stockQuantity)
    }

    @Test(expected = NotEnoughStockException::class)
    @DisplayName("주문 재고 수량 초과")
    fun orderItemExcessStockTest(){

        // given
        val member: Member = createMember()
        val item : Item = createBook("kotlin in action", 15000, 5)
        var orderCount = 6

        //when
        orderService.order(member.id, item.id, orderCount)

        // then
        fail("재고 수량 부족 예외가 발생해야 한다.")
    }

    @Test
    @DisplayName("주문 취소")
    fun cancelOrderTest(){

        // given
        val member : Member = createMember()
        val item : Item = createBook("kotlin in action", 10000, 20)
        val orderCount = 2

        var orderId : Long = orderService.order(member.id, item.id, orderCount)

        var getOrder: Order = orderRepository.findOrderById(orderId)

        // when
        assertEquals("주문 취소전 상태는 ORDER 이다.", OrderStatus.ORDER, getOrder.status)
        assertEquals("주문된 상품은 그 만큼 재고가 감소해야 한다.", 18, item.stockQuantity)

        orderService.cancelOrder(orderId)

        // then
        assertEquals("주문 취소시 상태는 Cancel 이다.", OrderStatus.CANCEL, getOrder.status)
        assertEquals("주문이 취소된 상품은 그 만큼 재고가 증가해야 한다.", 20, item.stockQuantity)
    }

    private fun createMember(): Member {
        var member: Member = Member("heather", Address("seoul","mapo","1234"))
        em.persist(member)
        return member
    }

    private fun createBook(name:String, price:Int, stockQuantity:Int) : Book{
        var book : Book = Book(name,price,stockQuantity,"woo","4444")
        em.persist(book)
        return book
    }
}