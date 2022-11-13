package com.ithub.bigbrotherbackend.telegram.exception

import com.ithub.bigbrotherbackend.error.ApiException

class WrongPhoneNumberException(phoneNumber: String) : ApiException("Wrong phone number $phoneNumber")

class TokenIsDeadException : ApiException("Token is dead.")

class WrongCodeException : ApiException("Code is wrong, try another.")

class PhoneNumberInvalidException(phoneNumber: String) : ApiException("Phone number $phoneNumber is invalid.")

class AlreadyClientCreatedException(chat: Long, respondentId: Long) :
    ApiException("Client with chat: $chat and respondentId: $respondentId already exists.")