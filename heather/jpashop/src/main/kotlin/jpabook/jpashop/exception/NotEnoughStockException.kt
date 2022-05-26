package jpabook.jpashop.exception

class NotEnoughStockException() : RuntimeException() {

    constructor(message: String) : this() {}

    constructor(cause: Throwable) : this() {}

    constructor(message:String, cause:Throwable) : this(){}

}