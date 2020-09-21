package com.rtchubs.engineerbooks.event

class ErrorMessageEvent(val message: String, val showInAlert:Boolean)
class LoadingEvent(val loading: Boolean)
class RefreshTokenExpireEvent
