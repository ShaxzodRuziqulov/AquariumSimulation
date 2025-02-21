package org.example;


import java.util.Random;

class Fish implements Runnable {
    private static final Random random = new Random();
    private static final int MIN_LIFESPAN = 10;
    private static final int MAX_LIFESPAN = 25;

    private final String gender;
    private final int lifespan;
    private final Aquarium aquarium;
    private boolean alive = true;
    private int x, y;

    public Fish(String gender, Aquarium aquarium, int x, int y) {
        this.gender = gender;
        this.lifespan = random.nextInt(MAX_LIFESPAN - MIN_LIFESPAN + 1) + MIN_LIFESPAN;
        this.aquarium = aquarium;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < lifespan; i++) {
                Thread.sleep(1000);
                move();
                aquarium.checkForBreeding(this);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            alive = false;
            aquarium.removeFish(this);
        }
    }

    private void move() {
        x += random.nextInt(3) - 1;
        y += random.nextInt(3) - 1;
        x = Math.max(0, Math.min(x, aquarium.getWidth() - 1));
        y = Math.max(0, Math.min(y, aquarium.getHeight() - 1));
    }
    public String getGender() {
        return gender;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}