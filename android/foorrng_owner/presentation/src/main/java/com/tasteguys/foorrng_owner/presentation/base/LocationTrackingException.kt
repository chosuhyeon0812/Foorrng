package com.tasteguys.foorrng_owner.presentation.base

sealed interface LocationTrackingException{
    class LocationNullException : LocationTrackingException
}
