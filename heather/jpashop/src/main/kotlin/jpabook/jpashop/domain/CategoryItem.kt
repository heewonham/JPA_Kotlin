package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.*
import javax.persistence.*

@Entity
class CategoryItem (

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "item_id")
        var item: Item,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: Category,

        var group: String
) : BaseEntity() {}