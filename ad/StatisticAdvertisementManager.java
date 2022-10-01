package com.javarush.task.task27.task2712.ad;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager statisticAdvertisementManager;
    private final AdvertisementStorage advertisementStorage;

    private StatisticAdvertisementManager() {
        advertisementStorage = AdvertisementStorage.getInstance();
    }

    public static synchronized StatisticAdvertisementManager getInstance(){
        if(statisticAdvertisementManager == null){
            statisticAdvertisementManager = new StatisticAdvertisementManager();
        }
        return statisticAdvertisementManager;
    }

    public List<Advertisement> getActiveAdvertisement(){
        return advertisementStorage.list().stream()
                .filter(advertisement -> advertisement.isActive())
                .collect(Collectors.toList());
    }

    public List<Advertisement> getArchivedAdvertisement(){
        return advertisementStorage.list().stream()
                .filter(advertisement -> !advertisement.isActive())
                .collect(Collectors.toList());
    }
}
