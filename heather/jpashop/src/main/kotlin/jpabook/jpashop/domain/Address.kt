package jpabook.jpashop.domain

import javax.persistence.Embeddable

// 주의사항 : setter을 넣지 않는다.
@Embeddable
class Address (
    val city:String,
    val street:String,
    val zipcode:String
    )