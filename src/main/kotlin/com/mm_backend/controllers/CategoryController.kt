package com.mm_backend.controllers

import com.mm_backend.constants.MSG_USER_NOT_EXIST
import com.mm_backend.utils.badRequest
import com.mm_backend.utils.ok
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("category")
class CategoryController: BaseController() {
    @GetMapping("get-all")
    fun getAllCategoriesOfUser(): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        return ok(categoryService.getCategoriesForUser(userId))
    }
}