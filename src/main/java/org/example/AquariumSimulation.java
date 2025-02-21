package org.example;

import java.util.Random;

public class AquariumSimulation {
    public static void main(String[] args) {
        Random random = new Random();
        int maleCount = random.nextInt(5) + 1;
        int femaleCount = random.nextInt(5) + 1;

        Aquarium aquarium = new Aquarium(10, 10, 15);

        for (int i = 0; i < maleCount; i++) {
            aquarium.addFish(new Fish("Erkak", aquarium, random.nextInt(10), random.nextInt(10)));
        }

        for (int i = 0; i < femaleCount; i++) {
            aquarium.addFish(new Fish("Urg'ochi", aquarium, random.nextInt(10), random.nextInt(10)));
        }

        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        aquarium.shutdown();
    }
}