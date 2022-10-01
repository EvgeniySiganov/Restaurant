package com.javarush.task.task27.task2712;


import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet{
    static Logger logger = Logger.getLogger(Tablet.class.getName());
    private final int number;
    private AdvertisementManager advertisementManager;
    private LinkedBlockingQueue queue;

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    public void createOrder(){
        Order userOrder = null;
        try {
            userOrder = new Order(this);
            if (userOrder.isEmpty()) {
                return;
            }
            processOrder(userOrder);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    public void createTestOrder(){
        try {
            TestOrder testOrder = new TestOrder(this);
            processOrder(testOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processOrder(Order order){
        try {
            advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            advertisementManager.processVideos();
        }catch (NoVideoAvailableException e){
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
        try {
            queue.put(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("Tablet{number=%d}", number);
    }
}
