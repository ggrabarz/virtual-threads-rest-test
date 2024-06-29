package com.example.restasync.service;

import com.example.restasync.client.FibonacciRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.math.BigInteger;
import java.time.Duration;


@Service
@RequiredArgsConstructor
public class FibonacciService {
    private final FibonacciRestClient fibonacciRestClient;
    private static final Integer LEFT_TERM_MAX_THREADS = 32 * 1024;
    private static final Integer RIGHT_TERM_MAX_THREADS = LEFT_TERM_MAX_THREADS / 2;
    Scheduler leftS = Schedulers.newBoundedElastic(LEFT_TERM_MAX_THREADS, Integer.MAX_VALUE, "leftS", 5, true);
    Scheduler leftP = Schedulers.newBoundedElastic(LEFT_TERM_MAX_THREADS, Integer.MAX_VALUE, "leftP", 5, true);
    Scheduler rightS = Schedulers.newBoundedElastic(RIGHT_TERM_MAX_THREADS, Integer.MAX_VALUE, "rightS", 5, true);
    Scheduler rightP = Schedulers.newBoundedElastic(RIGHT_TERM_MAX_THREADS, Integer.MAX_VALUE, "rightP", 5, true);
    Scheduler subscribe = Schedulers.newBoundedElastic(32 * 1024, Integer.MAX_VALUE, "subscribe", 5, true);
    Scheduler publish = Schedulers.newBoundedElastic(32 * 1024, Integer.MAX_VALUE, "publish", 5, true);

    public Mono<BigInteger> computeNthFibonacciTerm(Integer term) {
        if (term < 0) {
            throw new IllegalArgumentException("Mr Fibonacci does not approve.");
        }

        if (term == 0) {
            return Mono.just(BigInteger.ZERO);
        }

        if (term == 1) {
            return Mono.just(BigInteger.ONE);
        }

        Duration delay = Duration.ofMillis(200);
        Mono<BigInteger> leftTerm = computeNthFibonacciTermOverNetwork(term - 1).subscribeOn(leftS).delayElement(delay).publishOn(leftP);
        Mono<BigInteger> rightTerm = computeNthFibonacciTermOverNetwork(term - 2).subscribeOn(rightS).delayElement(delay).publishOn(rightP);
        return Flux.merge(leftTerm, rightTerm).delayElements(delay).subscribeOn(subscribe).publishOn(publish).reduce(BigInteger::add);
    }

    private Mono<BigInteger> computeNthFibonacciTermOverNetwork(Integer term) {
        return fibonacciRestClient.computeNthFibonacciTerm(term);
    }
}
