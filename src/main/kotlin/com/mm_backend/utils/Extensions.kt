package com.mm_backend.utils

import com.mm_backend.dto.response.BaseResponse
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity

fun badRequest(msg: String = "")
    = ResponseEntity.badRequest().body(BaseResponse(code = -1, msg = msg))

fun ok(msg: String = "success")
    = ResponseEntity.ok().body(BaseResponse(code = 0, msg = msg))

fun ok(body: Any)
        = ResponseEntity.ok().body(body)