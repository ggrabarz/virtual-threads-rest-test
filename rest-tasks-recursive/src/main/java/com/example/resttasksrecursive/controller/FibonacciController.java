package com.example.resttasksrecursive.controller;

import com.example.resttasksrecursive.service.FibonacciService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FibonacciController {
    private final FibonacciService fibonacciService;

    @GetMapping(value = "/fibo/{term}", produces = "application/json")
    public ResponseEntity<BigInteger> computeNthFibonacciTerm(@PathVariable Integer term) {
        log.info("Computing term {} in thread {}", term, Thread.currentThread());
        return ResponseEntity.ok(fibonacciService.computeNthFibonacciTerm(term));
    }
}
