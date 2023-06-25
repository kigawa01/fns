package net.kigawa.fns

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FnsApplication

fun main(args: Array<String>) {
  runApplication<FnsApplication>(*args)
}
