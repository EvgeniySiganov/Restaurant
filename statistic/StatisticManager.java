package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager statisticManager;
    private StatisticStorage statisticStorage = new StatisticStorage();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    private StatisticManager() {
    }

    public static synchronized StatisticManager getInstance(){
        if(statisticManager == null){
            statisticManager = new StatisticManager();
        }
        return statisticManager;
    }

    public void register(EventDataRow data){
        statisticStorage.put(data);
    }

    public Map<Date, Long> getAmountPerDays(){
        Map<Date, Long> dateLongMap = new HashMap<>();
        List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.SELECTED_VIDEOS);
        for (EventDataRow e : eventDataRowList) {
            VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow) e;
            String format = simpleDateFormat.format(videoSelectedEventDataRow.getDate());
            try {
                Date date = simpleDateFormat.parse(format);
                if(!dateLongMap.containsKey(date)){
                    dateLongMap.put(date, videoSelectedEventDataRow.getAmount());
                }else {
                    Long plusAmount = dateLongMap.get(date) + videoSelectedEventDataRow.getAmount();
                    dateLongMap.put(date, plusAmount);
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return dateLongMap;
    }

    public Map<Date, Map<String, Integer>> getCookingTimeByCookPerDays(){
        Map<Date, Map<String, Integer>> mapMap = new HashMap<>();
        List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.COOKED_ORDER);
        for (EventDataRow e: eventDataRowList){
            CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) e;
            String cookName = cookedOrderEventDataRow.getCookName();
            Integer cookingTime = cookedOrderEventDataRow.getTime();
            String format = simpleDateFormat.format(cookedOrderEventDataRow.getDate());
            try{
                Date date = simpleDateFormat.parse(format);
                if(!mapMap.containsKey(date)){
                    Map<String, Integer> stringIntegerMap = new HashMap<>();
                    stringIntegerMap.put(cookName, cookingTime);
                    mapMap.put(date, stringIntegerMap);
                }else {
                    if(!mapMap.get(date).containsKey(cookName)){
                        mapMap.get(date).put(cookName, cookingTime);
                    }else {
                        mapMap.get(date).put(cookName, mapMap.get(date).get(cookName) + cookingTime);
                    }
                }
            }catch (ParseException exception){
                exception.printStackTrace();
            }
        }
        return mapMap;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType e : EventType.values()) {
                storage.put(e, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data){
            EventType type = data.getType();
            if(!this.storage.containsKey(type)){
                throw new UnsupportedOperationException();
            }
            storage.get(type).add(data);
        }

        private List<EventDataRow> get(EventType type){
            if(!this.storage.containsKey(type)){
                throw new UnsupportedOperationException();
            }
            return storage.get(type);
        }
    }
}
