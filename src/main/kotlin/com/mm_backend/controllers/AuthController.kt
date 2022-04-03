package com.mm_backend.controllers

import com.mm_backend.constants.MSG_INCORRECT_AUTH_INFO
import com.mm_backend.constants.MSG_USER_EXIST
import com.mm_backend.dto.request.AuthRequest
import com.mm_backend.dto.request.RegisterRequest
import com.mm_backend.dto.response.AuthenticationResponse
import com.mm_backend.model.entities.AppUser
import com.mm_backend.services.UserService
import com.mm_backend.utils.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    val authenticationManager: AuthenticationManager,
    val userService: UserService,
    val jwtUtils: JwtUtils
) : BaseController() {
    val modelMapper = ModelMapper()

    @PostMapping("login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<*> {
        val authToken = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password,)
        return if(authenticationManager.authenticate(authToken).isAuthenticated) {
            val user = userService.getUserByUsername(authRequest.username)
            val jwtToken = jwtUtils.generateToken(user!!)
            ok(AuthenticationResponse(jwtToken))
        }
        else {
            badRequest(MSG_INCORRECT_AUTH_INFO)
        }
    }

    @PostMapping("register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<*> {
        if (userService.isUserExist(registerRequest.username))
            return badRequest(MSG_USER_EXIST)

        val newUser = modelMapper.map(registerRequest, AppUser::class.java)
        userService.saveUser(newUser)

        return ok()
    }

    @GetMapping("test")
    fun test(): ResponseEntity<*> {
        throw Exception("bhvbyjklvuyiov;vbil;vjvlh")
    }
}