package com.javarush.task.task27.task2712;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RandomOrderGeneratorTask implements Runnable{
    private int interval;
    private final List<Tablet> tablets;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.interval = interval;
        this.tablets = tablets;
    }

    @Override
    public void run() {
        int rand = (int) (Math.random() * tablets.size());
        Tablet tablet = tablets.get(rand);
        while (!Thread.currentThread().isInterrupted()){
            tablet.createTestOrder();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
