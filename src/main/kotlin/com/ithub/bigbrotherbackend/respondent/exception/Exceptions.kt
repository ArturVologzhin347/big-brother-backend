package com.ithub.bigbrotherbackend.respondent.exception

import com.ithub.bigbrotherbackend.error.ApiException

class RespondentWithNumberDoesntExists(phoneNumber: String): ApiException(
    "Respondent with phone number $phoneNumber doesn't exists"
)
