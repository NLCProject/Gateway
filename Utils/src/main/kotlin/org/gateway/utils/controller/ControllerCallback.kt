package org.gateway.utils.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.concurrent.Callable

object ControllerCallback {

    fun postOperation(task: Callable<*>): ResponseEntity<*> {
        task.call()
        return ResponseEntity(String(), HttpStatus.NO_CONTENT)
    }

    fun getOperation(task: Callable<*>): ResponseEntity<*> = ResponseEntity(task.call(), HttpStatus.OK)
}
