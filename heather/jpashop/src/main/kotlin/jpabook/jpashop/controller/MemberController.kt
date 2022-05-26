package jpabook.jpashop.controller

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.MemberForm
import jpabook.jpashop.service.MemberService
import org.springframework.boot.context.properties.bind.BindResult
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class MemberController(
    val memberService: MemberService
){
    @GetMapping("/members/new")
    fun createForm(model: Model): String{
        model.addAttribute("memberForm", MemberForm());
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Valid form:MemberForm, result: BindingResult): String{
        if(result.hasErrors()){
            return "members/createMemberForm"
        }

        val address: Address = Address(form.city, form.street, form.zipcode)
        var member: Member = Member(form.name, address)

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String{
        val members: MutableList<Member> = memberService.findMember()
        model.addAttribute("members", members)
        return "members/memberList"
    }
}