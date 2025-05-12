package com.mycompany.app;

import org.openqa.selenium.By;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Task3 {
    public static void fetchWeatherData() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            String weatherApiUrl = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            driver.get(weatherApiUrl);

            WebElement jsonElement = driver.findElement(By.tagName("pre"));
            String responseJson = jsonElement.getText();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseJson);

            JSONObject hourlyForecast = (JSONObject) jsonObject.get("hourly");
            JSONArray forecastTimes = (JSONArray) hourlyForecast.get("time");
            JSONArray hourlyTemperatures = (JSONArray) hourlyForecast.get("temperature_2m");
            JSONArray hourlyRainfall = (JSONArray) hourlyForecast.get("rain");

            PrintWriter outputWriter = new PrintWriter(new FileWriter("../result/forecast.txt"));

            outputWriter.println("+----+---------------------+--------------+-------------+");
            outputWriter.println("| №  |     Дата/время      | Температура  | Осадки (мм) |");
            outputWriter.println("+----+---------------------+--------------+-------------+");

            for (int index = 0; index < forecastTimes.size(); index++) {
                String timestamp = (String) forecastTimes.get(index);
                double temperature = (Double) hourlyTemperatures.get(index);
                double rainfall = (Double) hourlyRainfall.get(index);
                outputWriter.printf("| %-2d | %-19s | %-12.1f | %-11.2f |\n",
                        index + 1, timestamp, temperature, rainfall);
            }

            outputWriter.println("+----+---------------------+--------------+-------------+");

            outputWriter.close();
            System.out.println("Прогноз погоды успешно сохранён в result/forecast.txt");

        } catch (Exception ex) {
            System.out.println("Произошла ошибка при получении прогноза погоды.");
            System.out.println(ex.toString());
        } finally {
            driver.quit();
        }
    }
}
