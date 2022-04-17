package com.mm_backend.services

import com.mm_backend.model.entities.AppUser
import com.mm_backend.repos.AppUserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService: UserDetailsService {
    @Autowired
    lateinit var userRepo: AppUserRepo
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        if(username != null){
            val user = getUserByUsername(username) ?: throw UsernameNotFoundException("Username not found")
            return User(user.username,user.password, listOf(SimpleGrantedAuthority("USER")))
        }
        throw UsernameNotFoundException("username null")
    }

    fun getUserByUsername(username: String): AppUser? = userRepo.findByUsername(username)

    fun saveUser(user: AppUser) {
        user.password = passwordEncoder.encode(user.password)
        userRepo.save(user)
    }

    fun isUserExist(username: String): Boolean = getUserByUsername(username) != null
    fun isUserExist(userId: Long): Boolean = !userRepo.findById(userId).isEmpty

    fun flush() {
        userRepo.flush()
    }
}