package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.BaseEntity
import jpabook.jpashop.domain.CategoryItem
import jpabook.jpashop.exception.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item (

    var name:String,
    var price:Int,
    var stockQuantity:Int,

    /*
        * 강의에서는 Category와 item을 다대다 관계로 처리했지만
        * 실제로 다대다 관계는 매우 위험하므로,, 일대다 다대일 관계로 풀어서 사용한다.
    */
    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL])
    val categoryitems:MutableList<CategoryItem> = mutableListOf(),

    ) : BaseEntity() {
        // 비즈니스 로직
        fun addStock(quantity: Int) {
            this.stockQuantity += quantity
        }

        fun removeStock(quantity: Int) {
            val restStock = this.stockQuantity - quantity
            if(restStock < 0){
                throw NotEnoughStockException("need more stock")
                return
            }
            this.stockQuantity = restStock
        }
    }