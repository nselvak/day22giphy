package nus.iss.day22.giphy.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class SearchService {

    // set GIPHY_API_KEY=
    @Value("${giphy.api.key}")
    private String apiKey;

    final String URL = "https://api.giphy.com/v1/gifs/search";

    // setting default
    public List<String> searchGiphy(String query) {
        return searchGiphy(query, 10, "pg");

    }

    public List<String> searchGiphy(String query, String rating) {
        return searchGiphy(query, 10, rating);

    }

    public List<String> searchGiphy(String query, Integer limit) {
        return searchGiphy(query, limit, "pg");

    }


    public List<String> searchGiphy(String query, Integer limit, String rating) {

        List<String> list = new ArrayList<String>();
        String finalURL = UriComponentsBuilder
                    .fromUriString(URL)
                    .queryParam("api_key", apiKey)
                    .queryParam("q", query)
                    .queryParam("limit", limit)
                    .queryParam("rating", rating)
                    .toUriString();
        

        RequestEntity<Void> req = RequestEntity
                                .get(finalURL)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        // possbile to send error so try and catch 

        try {
            resp = template.exchange(req, String.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        } 
        

        // {} is json object
        // [] is json array
        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject(); // {} is json object
            
            JsonArray results = o.getJsonArray("data"); // [] is json array this is for data
            
            //System.out.println(">>>>> service results: " + results);

            for (int i=0; i <results.size(); i++) {
                JsonObject temp = results.getJsonObject(i);
                JsonObject temp1 =  (JsonObject) temp.get("images");
                JsonObject temp2 = (JsonObject)  temp1.get("fixed_width");
                String img = temp2.getString("url");

                // 
                // Strign image = temp.getJsonObject("images").getJsonObject("fixed_width").getString("url");


                list.add(img);
            }
            

        } catch (Exception ex) {
            System.err.printf(">>>> service Error: %s\n", ex.getMessage());
            ex.printStackTrace();
        }
        
        //System.out.println(">>>>> service list: " + list);
        return list;

    }
    
}
