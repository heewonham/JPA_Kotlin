package jpabook.jpashop.domain

import javax.validation.constraints.NotEmpty

class MemberForm (){
    @field:NotEmpty(message = "회원 이름은 필수입니다.")
    var name: String = ""
    @field:NotEmpty(message = "필수 입력 값 입니다.")
    var city: String = ""
    @field:NotEmpty(message = "필수 입력 값 입니다.")
    var street: String = ""
    @field:NotEmpty(message = "필수 입력 값 입니다.")
    var zipcode: String = ""
}