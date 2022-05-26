package jpabook.jpashop.domain

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Member(

    var name: String,

    @Embedded
    var address: Address?,

    @OneToMany(mappedBy = "member")
    val orders:MutableList<Order> = mutableListOf()

) : BaseEntity() {
}