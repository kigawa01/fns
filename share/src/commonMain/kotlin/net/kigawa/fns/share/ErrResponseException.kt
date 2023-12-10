package net.kigawa.fns.share

import net.kigawa.fns.share.json.ErrResponse

class ErrResponseException(errResponse: ErrResponse) : ErrIDException(
  errResponse.errID,
  message = errResponse.message
)