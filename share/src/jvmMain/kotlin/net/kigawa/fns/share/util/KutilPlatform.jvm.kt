package net.kigawa.fns.share.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object KutilPlatform {
  actual fun getSystemProperty(name: String): String = System.getProperty(name)
  actual fun getEnv(name: String): String? = System.getenv(name)
}