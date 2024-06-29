package com.example.restsyncvirtual.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;

@FeignClient(name = "synchronous-client", url = "${server.address}:${server.port}")
public interface FibonacciRestClient {

    @GetMapping(value = "/fibo/{term}", produces = "application/json")
    ResponseEntity<BigInteger> computeNthFibonacciTerm(@PathVariable Integer term);
}
