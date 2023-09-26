package net.kigawa.fns.backend.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
  @JsonProperty("access_token") val accessToken: String
)
