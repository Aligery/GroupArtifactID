package com.company;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//класс для получения и обработки значения доллара
public class ValueOfDollar {
    //метод для получения значения бакса на текущую дату

    public double getCurrentValue() throws IOException {
        double CurrentValue = 0;
        String AdressOfApi = "http://data.fixer.io/api/latest?access_key=4aa429213be45204485841a23ed3a788&symbols=USD,RUB&format=1";
        CurrentValue = ConnectToData(AdressOfApi, CurrentValue);
    return CurrentValue;
    }
//    http://data.fixer.io/api/2013-03-16?access_key=4aa429213be45204485841a23ed3a788&symbols=USD,RUB&format=1 - правильный формат запроса
        public double getEarlyValue (int Year, int Month, int Day) throws IOException {
            double CurrentValue = 0;
            String AdressOfApiSecond = "http://data.fixer.io/api/" + Year + "-";
            if ((Month < 10) && (Day < 10)) {
                AdressOfApiSecond = AdressOfApiSecond + "0" + Month + "-" + "0" + Day + "?access_key=4aa429213be45204485841a23ed3a788&symbols=USD,RUB&format=1";
            }
            if ((Month < 10) && (Day > 10)) {
                AdressOfApiSecond = AdressOfApiSecond + "0" + Month + "-" + Day + "?access_key=4aa429213be45204485841a23ed3a788&symbols=USD,RUB&format=1";
            }
            if ((Month > 10) && (Day < 10)) {
                AdressOfApiSecond = AdressOfApiSecond + Month + "-" + "0" + Day + "?access_key=4aa429213be45204485841a23ed3a788&symbols=USD,RUB&format=1";
            }
            if (Month > 10 && Day > 10) {
                AdressOfApiSecond = "http://data.fixer.io/api/" + Year + "-" + Month + "-" + Day + "?access_key=4aa429213be45204485841a23ed3a788&symbols=USD,RUB&format=1";
            }
            CurrentValue = ConnectToData(AdressOfApiSecond, CurrentValue);
            return CurrentValue;
        }
        private double ConnectToData(String AdressOfApi, double CurrentValue)
        {

            try {
                String url = AdressOfApi;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //print in String
                System.out.println(response.toString());
                //Read JSON response and print
                JSONObject myResponse = new JSONObject(response.toString());
                JSONObject form_rates = myResponse.getJSONObject("rates");
                System.out.println("result after Reading JSON Response");
                System.out.println("USD " + form_rates.getDouble("USD"));
                System.out.println("RUB " + form_rates.getDouble("RUB"));
                CurrentValue=form_rates.getDouble("RUB")/form_rates.getDouble("USD");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return CurrentValue;
        }
}
