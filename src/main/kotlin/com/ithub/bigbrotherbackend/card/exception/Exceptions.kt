package com.ithub.bigbrotherbackend.card.exception

import com.ithub.bigbrotherbackend.error.ApiException

class UnknownCardNumberException(cardNumber: String) :
    ApiException("Unknown card number: $cardNumber")

