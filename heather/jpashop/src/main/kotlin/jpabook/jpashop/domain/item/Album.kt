package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("album")
class Album (
    name:String,
    price:Int,
    stockQuantity: Int,

    var artist:String,
    var etc: String

    ) : Item(name, price, stockQuantity)