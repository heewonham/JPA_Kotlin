package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService (
    val memberRepository: MemberRepository
    ){

    @Transactional
    fun join(member:Member):Long{
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member){
        val findMembers = memberRepository.findByName(member.name)
        if(!findMembers.isEmpty()){
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    fun findMember() : MutableList<Member>{
        return memberRepository.findAll()
    }

    fun findById(memberId : Long): Member?{
        return memberRepository.findById(memberId)
    }
}