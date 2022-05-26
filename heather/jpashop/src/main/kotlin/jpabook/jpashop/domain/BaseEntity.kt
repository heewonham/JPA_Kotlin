package jpabook.jpashop.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Id
    @GeneratedValue
    var id :Long = 0,

    @CreatedDate
    @Column(name = "created_at")
    var createdAt : LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt : LocalDateTime? = null
) { }