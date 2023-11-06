package net.kigawa.fns.share

open class ErrorIDException(val errID: ErrID, message: String = errID.name) : RuntimeException(message)