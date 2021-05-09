package com.tong.base;

import com.tong.inter.Animal;

public class Dog implements Animal {
    @Override
    public void speack() {
        System.out.println("Dog wang");
    }
}
