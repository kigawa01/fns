package net.kigawa.fns.frontend.util.js

import web.cssom.FontSize

inline fun FontSize(
  size: String,
): FontSize =
  size.unsafeCast<FontSize>()
