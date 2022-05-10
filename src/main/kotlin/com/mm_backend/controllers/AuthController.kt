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
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils
) : BaseController() {
    @PostMapping("login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<*> {
        return try {
            val authToken = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
            authenticationManager.authenticate(authToken)
            val user = userService.getUserByUsername(authRequest.username)
            val jwtToken = jwtUtils.generateToken(user!!)
            ok(jwtToken)
        }
        catch (e: BadCredentialsException){
            unauthorized()
        }
    }

    @PostMapping("register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<*> {
        if (userService.isUserExist(registerRequest.username))
            return badRequest(MSG_USER_EXIST)

        val newUser = modelMapper.map(registerRequest, AppUser::class.java)
        userService.saveUser(newUser)
        userService.flush()
        categoryService.insertDefaultCategoriesForUser(newUser)

        return ok()
    }
}