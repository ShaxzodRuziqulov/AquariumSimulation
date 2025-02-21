package org.example;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Aquarium {
    private final int width, height, capacity;
    private final List<Fish> fishes = Collections.synchronizedList(new ArrayList<>());
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Random random = new Random();

    public Aquarium(int width, int height, int capacity) {
        this.width = width;
        this.height = height;
        this.capacity = capacity;
    }

    public synchronized void addFish(Fish fish) {
        if (fishes.size() < capacity) {
            fishes.add(fish);
            executorService.submit(fish);
            System.out.println(fish.getGender() + " baliq akvariumga qo'shildi. Jami baliqlar: " + fishes.size());
        }
    }

    public synchronized void removeFish(Fish fish) {
        fishes.remove(fish);
        System.out.println(fish.getGender() + " baliq vafot etdi. Jami baliqlar: " + fishes.size());
        if (fishes.isEmpty()) {
            System.out.println("Akvarium bo'sh, dastur to'xtatildi.");
            shutdown();
        }
    }

    public void checkForBreeding(Fish fish) {
        synchronized (fishes) {
            for (Fish other : fishes) {
                if (fish != other && fish.isAlive() && other.isAlive() &&
                        !fish.getGender().equals(other.getGender()) &&
                        fish.getX() == other.getX() && fish.getY() == other.getY()) {
                    spawnFish();
                    break;
                }
            }
        }
    }

    private void spawnFish() {
        if (fishes.size() >= capacity) return;
        String gender = random.nextBoolean() ? "Erkak" : "Urg'ochi";
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Fish newFish = new Fish(gender, this, x, y);
        addFish(newFish);
        System.out.println("Yangi " + gender + " baliq tug'ildi!");
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
