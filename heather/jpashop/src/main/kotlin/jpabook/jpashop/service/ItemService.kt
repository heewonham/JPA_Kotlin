package jpabook.jpashop.service

import jpabook.jpashop.domain.BookForm
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService (
    val itemRepository : ItemRepository
    ){

    @Transactional
    fun saveItem(item : Item){
        itemRepository.saveItem(item)
    }

    fun findItems() : MutableList<Item>{
        return itemRepository.findAll()
    }

    fun findOne(itemId: Long) : Item{
        return itemRepository.findItemById(itemId)
    }

    @Transactional
    fun updateItem(bookForm: BookForm){
        val item: Book = itemRepository.findItemById(bookForm.id) as Book
        item.name = bookForm.name
        item.price = bookForm.price
        item.stockQuantity  = bookForm.stockQuantity
        item.author = bookForm.author
        item.isbn = bookForm.isbn
    }

}