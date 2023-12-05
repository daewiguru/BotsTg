package ru.mathmeh.urfu.bot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
/**
 * The WeatherAPI class provides a simple interface to retrieve weather information
 * using the OpenWeatherMap API.
 */
public class WeatherAPI {
    /**
     * The API key required for accessing the OpenWeatherMap API.
     */
    private final String apiKey;
    /**
     * Constructs a WeatherAPI instance with the specified API key.
     *
     * @param apiKey The API key for accessing the OpenWeatherMap API.
     */
    public WeatherAPI(String apiKey) {
        this.apiKey = apiKey;
    }
    /**
     * Retrieves weather information for a specified city.
     *
     * @param city The name of the city for which weather information is requested.
     * @return A string containing the weather information, or an error message if the request fails.
     */
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
