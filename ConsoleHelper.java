package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishesSelected = new ArrayList<>();
        writeMessage(Dish.allDishesToString() + ": Choose yor dishes");
        String choice = readString();
        while (!choice.equals("exit")) {
            try {
                dishesSelected.add(Dish.valueOf(choice.toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                writeMessage("This dish not contains in restaurant, choose another");
            }
            choice = readString();
        }
        return dishesSelected;
    }
}
