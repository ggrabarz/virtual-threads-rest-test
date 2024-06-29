package com.example.resttasksrecursive.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;


@Service
@RequiredArgsConstructor
public class FibonacciService {
    public BigInteger computeNthFibonacciTerm(Integer term) {
        if (term < 0) {
            throw new IllegalArgumentException("Mr Fibonacci does not approve.");
        }

        return new FibonacciTerm(term).invoke();
    }

    @AllArgsConstructor
    private static class FibonacciTerm extends RecursiveTask<BigInteger> {
        private final Integer term;

        @Override
        protected BigInteger compute() {
            if (term == 0) {
                return BigInteger.ZERO;
            }
            if (term == 1) {
                return BigInteger.ONE;
            }
            FibonacciTerm leftTerm = new FibonacciTerm(term - 1);
            leftTerm.fork();
            return new FibonacciTerm(term - 2).compute().add(leftTerm.join());
        }
    }
}
