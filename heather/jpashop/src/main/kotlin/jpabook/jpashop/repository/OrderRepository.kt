package jpabook.jpashop.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderDto
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.OrderSearch
import jpabook.jpashop.domain.QMember.member
import jpabook.jpashop.domain.QOrder.order
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils.hasText
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class OrderRepository() {

    @PersistenceContext
    private lateinit var em:EntityManager

    fun save(order: Order): Long{
        em.persist(order)
        return order.id
    }

    fun findOrderById(id:Long) : Order{
        val _order : Order = em.find(Order::class.java, id)
        if(_order === null){
            throw IllegalStateException("존재하지 않는 주문입니다.")
        }
        return _order
    }

    // https://hjhng125.github.io/querydsl/querydsl-dynamic-query/
    fun findAll(orderSearch: OrderSearch): MutableList<Order>{
        return JPAQueryFactory(em)
            .select(order)
//                Projections.constructor(
//                    OrderDto::class.java,
//                    order.id,
//                    member.name,
//                    order.orderItems[0],
////                    order.orderItems[0].item.name,
////                    order.orderItems[0].orderPrice,
////                    order.orderItems[0].count,
//                    order.status,
//                    order.orderDate
//                )
//            )
            .from(order)
            .leftJoin(order.member, member)
            .where(condition(orderSearch))
            .fetch()

    }

    private fun condition(condition: OrderSearch): BooleanBuilder {
        var builder: BooleanBuilder = BooleanBuilder()

        if(hasText(condition.memberName)){
            builder.and(member.name.like("%${condition.memberName}%"))
        }

        if(condition.orderStatus != null){
            builder.and(order.status.eq(condition.orderStatus))
        }

        return builder
    }
}