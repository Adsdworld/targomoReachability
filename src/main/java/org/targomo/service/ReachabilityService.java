package org.targomo.service;

import org.targomo.model.Store;

import com.targomo.client.api.TravelOptions;
import com.targomo.client.api.enums.EdgeWeightType;
import com.targomo.client.api.enums.TravelType;
import com.targomo.client.api.exception.ResponseErrorException;
import com.targomo.client.api.exception.TargomoClientException;
import com.targomo.client.api.geo.DefaultSourceCoordinate;
import com.targomo.client.api.geo.DefaultTargetCoordinate;
import com.targomo.client.api.request.ReachabilityRequest;
import com.targomo.client.api.response.ReachabilityResponse;

import org.glassfish.jersey.message.GZipEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ReachabilityService {

    private static final String API_KEY = "ZEZWW4P6RUQSIQB4CBRG";
    private static final String SERVICE_URL = "https://api.targomo.com/germany/";

    public static int reach(Store sourceStore, List<Store> targetStores, boolean time) {

        int totalTravelTime = 0;

        // Options
        TravelOptions options = new TravelOptions();
        options.setEdgeWeightType(time ? EdgeWeightType.TIME : EdgeWeightType.DISTANCE); // choose between time and distance
        options.setMaxEdgeWeight(time ? 900 : 10000); // my account limitation 900s and 10km
        options.addSource(new DefaultSourceCoordinate(sourceStore.getId(), sourceStore.getLongitude(), sourceStore.getLatitude()));
        /** edgeWeight indications in REST Travel Times API
         * Determines the dimension of the edges' weight, i.e. time (distance in seconds) or distance (distance in meters).
         * distance Will optimize for distance and search for the shortest path, time will optimize for time and will search for the fastest path.
         * *Distance cannot be used in concert with travelType transit.*
         */
        if (time) { // to mesure time
            options.setTravelType(TravelType.CAR);
        }
        options.setServiceKey(API_KEY);
        options.setServiceUrl(SERVICE_URL);

        // Add targets
        for (Store target : targetStores) {
            if (!Objects.equals(sourceStore.getId(), target.getId())) {
                options.addTarget(new DefaultTargetCoordinate(target.getId(), target.getLongitude(), target.getLatitude()));
            }
        }

        Client client = ClientBuilder.newClient();
        client.register(GZipEncoder.class);

        // Make the request
        ReachabilityRequest request = new ReachabilityRequest(client, options);
        ReachabilityResponse response = null;
        try {
            response = request.get();
        } catch (TargomoClientException | ResponseErrorException e) {
            throw new RuntimeException(e);
        }

        // Map the response
        Map<String, Integer> travelTimes = response.getTravelTimes();

        // Get travel time of each store
        for (Store store : targetStores) {
            if (travelTimes.containsKey(store.getId()) && !Objects.equals(store.getId(), sourceStore.getId())) {
                totalTravelTime += travelTimes.get(store.getId());
            }
        }

        return totalTravelTime;
    }
}
