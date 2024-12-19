package org.srh.fda.model

data class OrderState(
    val amount: Int,
    val totalPrice: String
)

val OrderData = OrderState(
    amount = 0,
    totalPrice = "€0.0"
)
