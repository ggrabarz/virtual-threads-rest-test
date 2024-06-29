package com.example.restsyncvirtual.service;

import com.example.restsyncvirtual.client.FibonacciRestClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@Service
@RequiredArgsConstructor
public class FibonacciService {
    private final FibonacciRestClient fibonacciRestClient;

    public BigInteger computeNthFibonacciTerm(Integer term) {
        if (term < 0) {
            throw new IllegalArgumentException("Mr Fibonacci does not approve.");
        }

        if (term == 0) {
            return BigInteger.ZERO;
        }

        if (term == 1) {
            return BigInteger.ONE;
        }

        return ForkJoinTask
                .invokeAll(getTermComponents(term))
                .stream()
                .map(ForkJoinTask::join)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private List<RecursiveTask<BigInteger>> getTermComponents(Integer term) {
        return List.of(new FibonacciTask(term - 1), new FibonacciTask(term - 2));
    }

    @AllArgsConstructor
    private class FibonacciTask extends RecursiveTask<BigInteger> {
        private final Integer term;

        @Override
        protected BigInteger compute() {
            return fibonacciRestClient.computeNthFibonacciTerm(term).getBody();
        }
    }
}
