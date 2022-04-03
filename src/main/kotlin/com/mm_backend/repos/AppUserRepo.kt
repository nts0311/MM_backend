package com.mm_backend.repos

import com.mm_backend.model.entities.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserRepo: JpaRepository<AppUser, Long> {
    fun findByUsername(username: String): AppUser?
}