package anton.RestApi;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.*;


public class Client {

    private static final Double MAX_TEMPERATURE_VALUE = 100.0;
    private static final Double MIN_TEMPERATURE_VALUE = -100.0;
    private static final String urlPostMeasurement = "http://192.168.0.101:8080/measurements/add";
    private static final String urlPostSensor = "http://192.168.0.101:8080/sensors/registration";
    static final int NUMBER_OF_MEASUREMENTS = 1000;
    private static Random rnd = new Random();

    public static void main(String[] args) {

        String sensorName = "SuperSensor9";
        /**
         * Создаем новый Сенсор и в случае успеха добавляем 1000 измерений на сервер
         */
        if (createSensor(sensorName)){
             if (postMultipleMeasurements(NUMBER_OF_MEASUREMENTS, sensorName)) {
                 System.out.println(String.format("All %d measurements were successfully added!", NUMBER_OF_MEASUREMENTS));
             }
             else System.out.println("Something went wrong, please review the log files");
        }
    }

    /**
     * Метод для создания Сенсора методом POST
     */
    public static boolean createSensor(String sensorName){

        Map<String, Object> jsonToSend = new HashMap<>();
        jsonToSend.put("name",sensorName);
         if (makePostRequestsWithJSONData(urlPostSensor, jsonToSend))
             return true;
         else
             return false;
    }

    /**
     * Метод для создания одного Измерения у заданного Сенсора методом POST
     */
    public static boolean addMeasurement(double value, boolean raining, String sensorName){

        Map<String, Object> jsonToSend = new HashMap<>();

        jsonToSend.put("value", value );
        jsonToSend.put("raining", raining);
        jsonToSend.put("sensor", Map.of("name", sensorName));

        if (makePostRequestsWithJSONData(urlPostMeasurement, jsonToSend))
            return true;
        else
            return false;
    }

    /**
     * Общий метод POST для отправки данных в формате JSON на заданный URL
     */
    private static boolean makePostRequestsWithJSONData(String url, Map<String ,Object> jsonData){
        RestTemplate restTemplate  = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData,headers);

        String responsePostMeasurement;
        try {
            responsePostMeasurement = restTemplate.postForObject(url, request, String.class);
            if (responsePostMeasurement.equals("\"OK\"")){
                return true;
            }

        }
        catch (HttpClientErrorException e){
            System.out.println("Error");
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Отправляем заданное количество измерений на Сервер
     */
    private static boolean postMultipleMeasurements(int numberOfMeasurements, String sensorName){

        int counter = 0;

        for (int i = 0; i < numberOfMeasurements; i++) {
            double value = rnd.nextDouble(MAX_TEMPERATURE_VALUE - MIN_TEMPERATURE_VALUE - 1) + 1 + MIN_TEMPERATURE_VALUE;
            boolean raining = rnd.nextBoolean();
            addMeasurement(value, raining, sensorName);
            counter++;
        }
        // Проверяем, что дабавлено столько измерений сколько было задано
        if (counter == numberOfMeasurements){
            return true;
        }
        else
            return false;
    }
}
