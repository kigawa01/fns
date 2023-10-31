package net.kigawa.fns.backend.util

import net.kigawa.fns.share.ErrID

class ErrorIDException(val errID: ErrID, message: String = errID.name) : RuntimeException(message)