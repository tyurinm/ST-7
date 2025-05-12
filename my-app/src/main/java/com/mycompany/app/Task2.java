package com.mycompany.app;

import org.openqa.selenium.By;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {

    public static void fetchClientIp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://api.ipify.org/?format=json");

            WebElement jsonElement = driver.findElement(By.tagName("pre"));

            String jsonResponse = jsonElement.getText();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonResponse);

            String clientIp = (String) jsonObject.get("ip");

            System.out.println("Ваш IP-адрес: " + clientIp);

        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении IP-адреса.");
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
