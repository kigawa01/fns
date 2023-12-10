package net.kigawa.fns.share.util.logger

import java.util.logging.Level


fun LogLevel.javaLevel(): Level? {
  return Level.parse(this.name)
}