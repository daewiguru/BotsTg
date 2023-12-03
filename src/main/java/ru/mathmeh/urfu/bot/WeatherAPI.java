package ru.mathmeh.urfu.bot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherAPI {
    private final String apiKey;

    public WeatherAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getWeather(String city) {
        HttpClient httpClient = HttpClient.newHttpClient();

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Парсим JSON-ответ
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

                // Извлекаем температуру
                double temperature = json.getAsJsonObject("main").get("temp").getAsDouble();

                return String.format("Погода в %s: Температура: %.1f°C", city, temperature);
            } else {
                return "Не удалось получить данные о погоде. Код ошибки: " + response.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Произошла ошибка при выполнении запроса";
        }
    }
}

