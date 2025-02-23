package org.targomo.util;

import org.targomo.model.Store;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param path of the json file to parse
     * @return a list of Store
     * @throws IOException on error while reading tree
     */
    public static List<Store> parseStores(String path) throws IOException {


        List<Store> stores = new ArrayList<>(); // List of Store

        File jsonFile = new File(path);
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        // Add all the store List of Store
        for (JsonNode featureNode : rootNode.get("features")) {
            String id = featureNode.get("properties").get("id").asText();
            double longitude = featureNode.get("geometry").get("coordinates").get(0).asDouble(); // latitude of germany 10
            double latitude = featureNode.get("geometry").get("coordinates").get(1).asDouble(); // latitude of germany 51

            stores.add(new Store(id, longitude, latitude));
        }

        return stores;
    }
}
