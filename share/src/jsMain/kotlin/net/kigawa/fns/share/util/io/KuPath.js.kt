package net.kigawa.fns.share.util.io

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuPath actual constructor(strPath: String) {
  actual companion object {
    actual val separator: String
      get() = TODO("Not yet implemented")
  }

  actual fun join(child: String): KuPath {
    TODO("Not yet implemented")
  }

  actual fun join(child: KuPath): KuPath {
    TODO("Not yet implemented")
  }

  actual fun strPath(): String {
    TODO("Not yet implemented")
  }

  actual fun toAbsolute(): KuPath {
    TODO("Not yet implemented")
  }

  actual fun parent(): KuPath {
    TODO("Not yet implemented")
  }

  actual fun createDirOrGet(): KuDirectory {
    TODO("Not yet implemented")
  }

  actual fun toFile(): KuFile {
    TODO("Not yet implemented")
  }

  actual fun isExists(): Boolean {
    TODO("Not yet implemented")
  }

  actual fun removeIfExists() {
  }

}