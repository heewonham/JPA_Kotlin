package jpabook.jpashop.controller

import jpabook.jpashop.domain.BookForm
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController (
    val itemService: ItemService
    ){

    /**
    * 상품 등록 폼
    * */

    @GetMapping("/items/new")
    fun createForm(model: Model): String{
        model.addAttribute("form", BookForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(form: BookForm): String{
        var book: Book = Book(
            form.name,
            form.price,
            form.stockQuantity,
            form.author,
            form.isbn
        )
        itemService.saveItem(book)
        return "redirect:/items"
    }

    /**
     * 상품 목록
     * */
    @GetMapping("/items")
    fun list(model: Model): String{
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "items/itemList"
    }

    /**
     * 상품 수정 폼
     * */
    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model):String {
        var item:Book = itemService.findOne(itemId) as Book

        var form:BookForm = BookForm()
        form.id = item.id
        form.name = item.name
        form.price = item.price
        form.stockQuantity = item.stockQuantity
        form.author = item.author
        form.isbn = item.isbn

        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(@ModelAttribute("form") bookForm: BookForm): String {
        itemService.updateItem(bookForm)
        return "redirect:/items"
    }
}