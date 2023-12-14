package net.kigawa.fns.frontend.util.js

import web.cssom.ObjectPosition

inline fun ObjectPosition(
  x: ObjectPosition,
  y: ObjectPosition,
): ObjectPosition =
  "$x $y".unsafeCast<ObjectPosition>()