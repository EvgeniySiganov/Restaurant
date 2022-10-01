package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;
import com.javarush.task.task27.task2712.ad.AdvertisementManager;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException{
        this.tablet = tablet;
        initDishes();
        ConsoleHelper.writeMessage(toString());
    }

    protected void initDishes() throws IOException{
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public int getTotalCookingTime(){
        return dishes
                .stream()
                .mapToInt(Dish::getDuration)
                .sum();
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (dishes.size() == 0) return result.toString();
        result.append("Your order: [" + dishes.get(0));

        for (int i = 1; i < dishes.size(); i++) {
            result.append(", " + dishes.get(i).name());
        }
        result.append("] of " + tablet);
        result.append(", cooking time " + getTotalCookingTime() + "min");
        return result.toString();
    }
}