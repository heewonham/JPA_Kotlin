package jpabook.jpashop.repository

import jpabook.jpashop.domain.item.Item
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ItemRepository() {

    @PersistenceContext
    private lateinit var em : EntityManager

    fun saveItem(item:Item){
        em.persist(item)
    }

    fun findItemById(id:Long): Item{
        val _item:Item = em.find(Item::class.java, id)
        if(_item === null){
            throw IllegalStateException("존재하지 않는 아이템입니다.")
        }
        return _item
    }

    fun findAll(): MutableList<Item>{
        return em.createQuery("select i from Item i", Item::class.java)
            .resultList
    }

}