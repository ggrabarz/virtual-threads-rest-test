package com.example.restasync.controller;

import com.example.restasync.service.FibonacciService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FibonacciController {
    private final FibonacciService fibonacciService;

    @GetMapping(value = "/fibo/{term}", produces = "application/json")
    public Mono<ResponseEntity<BigInteger>> computeNthFibonacciTerm(@PathVariable Integer term) {
        return fibonacciService.computeNthFibonacciTerm(term).map(ResponseEntity::ok);
    }
}
