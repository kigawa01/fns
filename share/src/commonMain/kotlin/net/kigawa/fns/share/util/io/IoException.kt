package net.kigawa.fns.share.util.io

open class IoException(message: String?) : Exception(message) {
}

expect fun ioException(message: String?): IoException