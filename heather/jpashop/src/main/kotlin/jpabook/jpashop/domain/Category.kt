package jpabook.jpashop.domain

import javax.persistence.*

@Entity
class Category (
    @Id @GeneratedValue
    @Column(name="category_id")
    val id:Long,

    var name:String,

    /*
    * 강의에서는 Category와 item을 다대다 관계로 처리했지만
    * 실제로 다대다 관계는 매우 위험하므로,, 일대다 다대일 관계로 풀어서 사용한다.
    * */
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    val categoryitems: MutableList<CategoryItem> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    var parent: Category?,

    @OneToMany(mappedBy = "parent")
    val child: MutableList<Category> = mutableListOf()
){
    // 연관관계 메서드
    fun addChildCategory(child:Category){
        this.child.add(child)
        child.parent = this
    }
}