package com.mm_backend.security

import com.mm_backend.filter.ExceptionFilter
import com.mm_backend.filter.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(): WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity?) {
        http?.run {
            csrf().disable()
            sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            authorizeRequests().antMatchers("/auth/login/**").permitAll()
            authorizeRequests().antMatchers("/auth/register/**").permitAll()
            authorizeRequests().anyRequest().authenticated()
            addFilterBefore(ExceptionFilter(), UsernamePasswordAuthenticationFilter::class.java)
            addFilterBefore(JwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        }
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}

@Configuration
class PasswordEncoderConfig{
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}