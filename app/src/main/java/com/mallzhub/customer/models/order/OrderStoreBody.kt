package com.mallzhub.customer.models.order

data class OrderStoreBody(val customer_id: Int?, val merchant_id: Int?,
                          val purchase_number: String?, val invoice_number: String?,
                          val date_of_issue: String?, val amount_are: String?,
                          var customer_note: String?, val sub_total: Int?,
                          val tax_type_total: Int?, val discount_total: Int?,
                          val total: Int?, val amount_paid: Int?,
                          val amount_due: Int?, val list: List<OrderStoreProduct>?)

data class OrderStoreProduct(val product_id: Int?, val description: String?,
                          val unitType: String?, val unitValue: Int?,
                          val unitPrice: Int?, val taxType: Int?,
                          val taxTypeValue: String?, val discount: Int?,
                          val discountAmount: String?, val amount: Int?,
                          val vatAndTax: Any?)
