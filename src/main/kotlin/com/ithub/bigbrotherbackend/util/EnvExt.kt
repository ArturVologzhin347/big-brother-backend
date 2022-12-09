package com.ithub.bigbrotherbackend.util

import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles

fun Environment.profileIsActive(profile: String): Boolean {
    return acceptsProfiles(Profiles.of(profile))
}
