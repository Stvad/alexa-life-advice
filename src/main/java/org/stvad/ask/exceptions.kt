package org.stvad.ask

open class ASKException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)

class SlotMissingException(message: String? = null, cause: Throwable? = null) : ASKException(message, cause)