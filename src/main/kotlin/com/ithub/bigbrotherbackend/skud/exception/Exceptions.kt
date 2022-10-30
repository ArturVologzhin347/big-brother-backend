package com.ithub.bigbrotherbackend.skud.exception

import com.ithub.bigbrotherbackend.error.ApiException
import com.ithub.bigbrotherbackend.skud.model.SkudEvent

class WrongSkudEventTypeException(type: String) :
    ApiException("Type $type is wrong. Existing types: ${SkudEvent.Type.values()}")