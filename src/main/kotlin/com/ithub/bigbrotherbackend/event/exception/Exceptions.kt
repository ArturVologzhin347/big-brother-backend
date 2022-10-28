package com.ithub.bigbrotherbackend.event.exception

import com.ithub.bigbrotherbackend.error.ApiException
import com.ithub.bigbrotherbackend.event.scud.model.Event

class WrongEventTypeException(type: String) :
    ApiException("Type $type is wrong. Existing types: ${Event.Type.values()}")