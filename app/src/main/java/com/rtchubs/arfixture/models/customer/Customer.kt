package com.rtchubs.arfixture.models.customer

data class Customer(val id: Int?, val name: String?, val address: String?, val city: String?,
                    val state: String?, val zipcode: String?, val phone: String?,
                    val password: String?, val email: String?, val discountAmount: Int?,
                    val contact_person: String?, val merchant_id: Int?, val created_at: String?,
                    val updated_at: String?)