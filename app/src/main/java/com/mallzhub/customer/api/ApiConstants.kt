package com.mallzhub.customer.api

import com.mallzhub.customer.api.Api.API_REPO
import com.mallzhub.customer.api.Api.API_VERSION
import com.mallzhub.customer.api.Api.DIRECTORY_ACCOUNT
import com.mallzhub.customer.api.Api.DIRECTORY_BANK
import com.mallzhub.customer.api.Api.DIRECTORY_BANK_INFO
import com.mallzhub.customer.api.Api.DIRECTORY_CARD
import com.mallzhub.customer.api.Api.DIRECTORY_COMMON
import com.mallzhub.customer.api.Api.DIRECTORY_CONNECT
import com.mallzhub.customer.api.Api.DIRECTORY_PROFILE

object Api {
    private const val PROTOCOL = "https"
    //private const val API_ROOT = "backend.mallhubs.com"
    private const val API_ROOT = "backend.mobmalls.com"
    const val API_ROOT_URL = "$PROTOCOL://$API_ROOT"
    const val API_REPO = "api"
    const val API_VERSION = "v1"
    const val DIRECTORY_ACCOUNT = "account"
    const val DIRECTORY_CONNECT = "connect"
    const val DIRECTORY_BANK_INFO = "bankinformation"
    const val DIRECTORY_CARD = "banklink"
    const val DIRECTORY_BANK = "cardlink"
    const val DIRECTORY_PROFILE = "profile"
    const val DIRECTORY_COMMON= "common"
    const val ContentType = "Content-Type: application/json"
}

object ApiEndPoint {
    /* Registration */
    const val INQUIRE = "/$API_REPO/$API_VERSION/${DIRECTORY_ACCOUNT}/inquire"
    const val REQUESTOTP = "/$API_REPO/$API_VERSION/${DIRECTORY_ACCOUNT}/request-otp"
    const val REGISTRATION = "/$API_REPO/$API_VERSION/${DIRECTORY_ACCOUNT}"
    const val CONNECT_TOKEN = "/$API_REPO/$API_VERSION/${DIRECTORY_CONNECT}/token"
    const val GET_BANK_LIST = "/$API_REPO/$API_VERSION/${DIRECTORY_BANK_INFO}/bank-list"
    const val ADD_BANK = "/$API_REPO/$API_VERSION/${DIRECTORY_BANK}"
    const val ADD_CARD = "/$API_REPO/$API_VERSION/${DIRECTORY_CARD}"
    const val MY_ACCOUNT_LIST = "/$API_REPO/$API_VERSION/${DIRECTORY_PROFILE}/accounts"

    // eDokanPat
    const val ALL_MALL = "/$API_REPO/shopping-malls"
    const val ALL_MERCHANTS = "/$API_REPO/all-merchants"
    const val MERCHANT_PRODUCTS = "/$API_REPO/products-by-merchant/{id}"
    const val SALE = "/$API_REPO/sale"
    const val ORDER_LIST= "/$API_REPO/sales/filter-by-date/undefined/undefined/false"
    const val OFFER_LIST = "/$API_REPO/$DIRECTORY_COMMON/all/offers"
    const val PRODUCT_DETAILS = "/$API_REPO/$DIRECTORY_COMMON/{id}/product"
}

object ResponseCodes {
    const val CODE_SUCCESS = 200
    const val CODE_TOKEN_EXPIRE = 401
    const val CODE_UNAUTHORIZED = 403
    const val CODE_VALIDATION_ERROR = 400
    const val CODE_DEVICE_CHANGE = 451
    const val CODE_FIRST_LOGIN = 426
}

object ApiCallStatus {
    const val LOADING = 0
    const val SUCCESS = 1
    const val ERROR = 2
    const val EMPTY = 3
}
