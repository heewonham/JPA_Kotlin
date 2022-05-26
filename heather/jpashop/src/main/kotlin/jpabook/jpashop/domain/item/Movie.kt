package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("movie")
class Movie (
    name:String,
    price:Int,
    stockQuantity: Int,

    var director:String,
    var actor:String
) : Item(name, price, stockQuantity)