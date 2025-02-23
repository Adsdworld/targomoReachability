package org.targomo;

import org.targomo.model.Store;
import org.targomo.service.ReachabilityService;
import org.targomo.util.Parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = Paths.get("src", "main", "resources", "stores.json").toString();

        List<Store> stores = Parser.parseStores(filePath); // make sure to run from project root "targomo-reachability" folder

        // Display all stores from the stores.json
        System.out.println("\n>>stores.json:");
        for (Store store : stores) {
            System.out.printf("[%s]:\n\tLatitude: %s,\n\tLongitude %s%n", store.getId(), store.getLatitude(), store.getLongitude());
        }


        // Get, sort and display travel time from a source to multiple targets
        Map<String, Integer> storesByTravelTime = new HashMap<>();
        for (Store store : stores) {
            storesByTravelTime.put(store.getId(), ReachabilityService.reach(store, stores, true));
        }
        System.out.println("\nStores sorted by travel time:");
        storesByTravelTime.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(store -> System.out.printf("\tStore [%s] has total travel time of: %d s\n",
                        store.getKey(), store.getValue()));


        // Get, sort and display distance from a source to multiple targets
        Map<String, Integer> storesByDistance = new HashMap<>();
        for (Store store : stores) {
            storesByDistance.put(store.getId(), ReachabilityService.reach(store, stores, false));
        }
        System.out.println("\nStores sorted by distance:");
        storesByDistance.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(store -> System.out.printf("\tStore [%s] has total distance of: %d m\n",
                        store.getKey(), store.getValue()));
    }
}
