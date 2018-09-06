package com.goodsogood.ows.controller

import com.goodsogood.log4j2cm.annotation.HttpMonitorLogger
import com.goodsogood.ows.component.Errors
import com.goodsogood.ows.configuration.Global
import com.goodsogood.ows.exception.ApiException
import com.goodsogood.ows.model.vo.Result
import com.goodsogood.ows.model.UserEntity
import com.goodsogood.ows.service.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/user")
class UserController(
        @Autowired val errors: Errors,
        @Autowired val userService: UserService
) {
    val logger: Logger = LogManager.getLogger(UserController::class.java)

    @HttpMonitorLogger
    @GetMapping("/get")
    fun get(@RequestParam id: Long): ResponseEntity<Result<UserEntity>> {
        logger.debug("id->{}", id)
        val userEntity: UserEntity = userService.findOne(id)
                ?: throw ApiException("404", Result<UserEntity>(errors, Global.Errors.NOT_FOUND, HttpStatus.OK.value(), "用户"))
        return ResponseEntity(Result(userEntity, errors), HttpStatus.OK)
    }

    @HttpMonitorLogger
    @PostMapping("/add")
    fun add(@RequestBody userEntity: UserEntity): ResponseEntity<Result<Long?>> {
        logger.debug("userEntity->{}", userEntity)
        return ResponseEntity(Result(userService.addOne(userEntity), errors), HttpStatus.OK)
    }
}