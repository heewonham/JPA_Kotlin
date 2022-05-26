package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.junit.Assert.fail
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@Transactional
@RunWith(SpringRunner::class)
class MemberServiceTest(){

    @Autowired
    lateinit var memberService : MemberService
    @Autowired
    lateinit var memberRepository : MemberRepository

    @Test
    @DisplayName("Member 저장")
    fun joinTest(){

        // given
        val address = Address("seoul", "mapo","1234")
        var member : Member = Member("heather",address)

        // when
        var saveId: Long = memberService.join(member)

        // then
        //assertEquals(member, memberRepository.findById(saveId))
    }

    @Test
    @DisplayName("중복 Member 체크")
    fun duplicateMemberTest() {
        //given
        val address = Address("seoul", "mapo","1234")
        var member1 : Member = Member("heather",address)
        var member2 : Member = Member("heather", address)

        // when
        memberService.join(member1)
        memberService.join(member2)

        // then
        fail("예외가 발생해야 한다.")

    }
}