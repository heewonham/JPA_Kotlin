package jpabook.jpashop

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.domain.item.Item
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class initDb (
    val initService: InitService
    ){

    @PostConstruct
    fun init(){
        initService.doInit1()
    }

    @Component
    @Transactional
    class InitService(){

        @PersistenceContext
        lateinit var em: EntityManager

        fun doInit1(){
            val member1: Member = Member("heather", Address("평택","안중","1235"))
            val member2: Member = Member("hami", Address("서울","마포","9434"))

            em.persist(member1)
            em.persist(member2)

            val item1: Book = Book("JPA",15000, 30, "kim", "o0o0o0")
            val item2: Book = Book("slime", 20000, 100, "ham", "i9i9i9")

            em.persist(item1)
            em.persist(item2)
        }
    }
}