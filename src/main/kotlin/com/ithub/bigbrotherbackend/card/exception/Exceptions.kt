package com.ithub.bigbrotherbackend.card.exception

import com.ithub.bigbrotherbackend.error.ApiException

class UnknownCardNumberException(cardNumber: String) :
    ApiException("Unknown card number: $cardNumber")

class CardNotAssociatedException(cardNumber: String) :
        ApiException("Card with number: $cardNumber not associated to student.")
