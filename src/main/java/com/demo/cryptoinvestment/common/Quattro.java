package com.demo.cryptoinvestment.common;

public class Quattro<F, S, T, U> {
    private F first;
    private S second;
    private T third;
    private U fourth;

    private Quattro(F first, S second, T third, U fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public static <F, S, T, U> Quattro<F, S, T, U> of(F first, S second, T third, U fourth) {
        return new Quattro<>(first, second, third, fourth);
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public T getThird() {
        return this.third;
    }

    public U getFourth() {
        return this.fourth;
    }
}
