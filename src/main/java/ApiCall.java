import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

public class ApiCall {

    static ArrayList getRepos(String path, String authorization) throws IOException, InterruptedException {

        final String BASE_URL = "https://api.github.com/users/";
        ObjectMapper mapper = new ObjectMapper();

        var request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + path))
                .setHeader("Authorization", authorization)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList jsonList = mapper.readValue(response.body(), ArrayList.class);
        ArrayList<Map> jsonListOfMaps = new ArrayList<Map>();

        for (var json : jsonList) {

            Map jsonMap = mapper.convertValue(json, Map.class);
            jsonListOfMaps.add(jsonMap);

        }

        return jsonListOfMaps;
    }

    static ArrayList getCommits(String path, String authorization) throws IOException, InterruptedException {

        final String BASE_URL = "https://api.github.com/repos/";
        ObjectMapper mapper = new ObjectMapper();

        var request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + path))
                .setHeader("Authorization", authorization)
                .GET()
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ArrayList jsonList = mapper.readValue(response.body(), ArrayList.class);

            ArrayList<Map> jsonListOfMaps = new ArrayList<Map>();

            for (var json : jsonList) {

                Map jsonMap = mapper.convertValue(json, Map.class);
                jsonListOfMaps.add(jsonMap);

            }

            return jsonList;

    }


}