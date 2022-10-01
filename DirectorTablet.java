package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DirectorTablet {
    StatisticManager statisticManager = StatisticManager.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    StatisticAdvertisementManager statisticAdvertisementManager = StatisticAdvertisementManager.getInstance();

    public void printAdvertisementProfit(){
        long sum = 0;
        Map<Date, Long> sorted = statisticManager.getAmountPerDays()
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        for (Map.Entry<Date, Long> e : sorted.entrySet()) {
            long amountPerDay = e.getValue();
            if(amountPerDay > 0) {
                sum += amountPerDay;
                System.out.print(simpleDateFormat.format(e.getKey()));
                System.out.print(" - ");
                System.out.println(String.format(Locale.ENGLISH, "%.2f", amountPerDay / 100d));
            }
        }
        System.out.println(String.format("Total - %.2f", sum / 100d));
    }

    public void printCookWorkloading(){
        Map<Date, Map<String, Integer>> sortedMapMap = statisticManager.getCookingTimeByCookPerDays()
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        for (Map.Entry<Date, Map<String, Integer>> e : sortedMapMap.entrySet()) {
            Map<String, Integer> sortedMap = e.getValue()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e2, LinkedHashMap::new));
            System.out.println(simpleDateFormat.format(e.getKey()));
            for (Map.Entry<String, Integer> ee : e.getValue().entrySet()) {
                System.out.print(ee.getKey());
                System.out.print(" - ");
                System.out.println((int)Math.ceil(ee.getValue() / 60.0) + " min");
                System.out.println();
            }
        }
    }

    public void printArchivedVideoSet(){
        for (Advertisement a : sortingAdvertisement(statisticAdvertisementManager.getActiveAdvertisement())) {
            String s = String.format("%s - %d", a.getName(), a.getHits());
            ConsoleHelper.writeMessage(s);
        }
    }

    public void printActiveVideoSet(){
        for (Advertisement a : sortingAdvertisement(statisticAdvertisementManager.getArchivedAdvertisement())) {
            String s = String.format("%s", a.getName());
            ConsoleHelper.writeMessage(s);
        }
    }

    private List<Advertisement> sortingAdvertisement(List<Advertisement> list){
        return list
                .stream()
                .sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()))
                .collect(Collectors.toList());
    }
}
