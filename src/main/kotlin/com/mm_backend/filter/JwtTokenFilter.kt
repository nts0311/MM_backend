package com.mm_backend.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.mm_backend.utils.JwtUtils
import com.mm_backend.utils.unauthorized
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter: OncePerRequestFilter() {
    private val shouldNotFilterPaths = listOf(
        "/auth/login",
        "/auth/register"
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (shouldNotFilter(request.servletPath)){
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            runCatching {
                val jwtToken = authHeader.substring("Bearer ".length)
                val verifier = JWT.require(Algorithm.HMAC256(JwtUtils.SECRET.toByteArray())).build()
                val decodedJwt = verifier.verify(jwtToken)
                val username = decodedJwt.subject
                val authorities = listOf(SimpleGrantedAuthority("USER"))
                val authToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                SecurityContextHolder.getContext().authentication = authToken
                filterChain.doFilter(request, response)
            }
                .onFailure {
                    response.status = HttpStatus.FORBIDDEN.value()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, unauthorized())
                }
        }
        else {
            filterChain.doFilter(request, response)
        }
    }

    private fun shouldNotFilter(servletPath: String): Boolean = shouldNotFilterPaths.contains(servletPath)
}