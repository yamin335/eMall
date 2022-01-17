package com.rtchubs.shop.models.order

data class OrderStoreResponse(val code: Int?, val status: String?,
                              val message: String?, val data: OrderStoreResponseData?)

data class OrderStoreResponseData(val sale: OrderStoreSale?)

data class OrderStoreSale(val customer_id: Int?, val merchant_id: Int?,
                          val date: String?, val status: String?,
                          val YourReference: Any?, val OurReference: String?,
                          val amount_are: String?, val customer_note: String?,
                          val discount_type_total: Int?, val tax_type_total: Int?,
                          val sub_total: Int?, val grand_total: Int?, val paid_amount: Int?, val due_amount: Int?, val updated_at: String?, val created_at: String?, val id: Int?)