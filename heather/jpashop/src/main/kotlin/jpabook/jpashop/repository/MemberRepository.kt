package jpabook.jpashop.repository

import jpabook.jpashop.domain.Member
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository() {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun save(member: Member):Long{
        em.persist(member)
        return member.id;
    }

    fun findById(id:Long): Member{
        var _member : Member? = em.find(Member::class.java, id)
        if(_member === null){
            throw IllegalStateException("찾는 멤버가 없습니다.")
        }
        return _member
    }

    fun findAll() : MutableList<Member>{
        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }

    fun findByName(name: String) : MutableList<Member>{
        return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }
}
