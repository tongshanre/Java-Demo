package com.tong.agentmain;

public class AgentMain {
    public static void main(String[] args) throws InterruptedException {

        while (true) {
            Dog dog = new Dog();
            dog.speak();
            Thread.sleep(3000);
        }
    }
}
