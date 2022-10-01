package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    private final String name;
    private final StatisticManager statisticManager = StatisticManager.getInstance();
    private LinkedBlockingQueue queue;

    public Cook(String name) {
        this.name = name;
    }

    public void setQueue(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        ConsoleHelper.writeMessage(name + " Start cooking - " + order);

        int totalCookingTime = order.getTotalCookingTime();
        CookedOrderEventDataRow row = new CookedOrderEventDataRow(order.getTablet().toString(), name, totalCookingTime * 60, order.getDishes());
        StatisticManager.getInstance().register(row);

        try {
            Thread.sleep(totalCookingTime * 10);
        } catch (InterruptedException ignored) {
        }

        setChanged();
        notifyObservers(order);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                if (!queue.isEmpty()) {
                    this.startCookingOrder((Order) queue.take());
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
