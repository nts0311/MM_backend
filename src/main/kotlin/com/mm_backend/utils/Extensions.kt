package com.mm_backend.utils

import com.mm_backend.constants.MSG_INCORRECT_AUTH_INFO
import com.mm_backend.dto.response.BaseResponse
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun badRequest(msg: String = "")
    = ResponseEntity.badRequest().body(BaseResponse(code = -1, msg = msg))

fun unauthorized()
        = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse(code = -1, msg = MSG_INCORRECT_AUTH_INFO))

fun ok() = ResponseEntity.ok().body(BaseResponse(code = 0, msg = "success"))

fun ok(body: Any)
        = ResponseEntity.ok().body(BaseResponse(code = 0, msg = "success", data = body))