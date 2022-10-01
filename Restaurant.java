package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static List<Tablet> tablets = new ArrayList<>();
    private final static LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>();
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(ORDER_QUEUE);
            tablets.add(tablet);
        }

        DirectorTablet directorTablet = new DirectorTablet();

        Cook cook = new Cook("Amigo");
        cook.setQueue(ORDER_QUEUE);
        Cook cook2 = new Cook("Капитан");
        cook2.setQueue(ORDER_QUEUE);

        Waiter waiter = new Waiter();

        cook.addObserver(waiter);

        RandomOrderGeneratorTask randomOrderGeneratorTask = new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL);
        Thread threadProducer = new Thread(randomOrderGeneratorTask);
        Thread threadConsumer1 = new Thread(cook);
        Thread threadConsumer2 = new Thread(cook2);
        threadConsumer1.setDaemon(true);
        threadConsumer2.setDaemon(true);
        threadProducer.start();
        threadConsumer1.start();
        threadConsumer2.start();

        Thread.sleep(1000);
        threadProducer.interrupt();

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
