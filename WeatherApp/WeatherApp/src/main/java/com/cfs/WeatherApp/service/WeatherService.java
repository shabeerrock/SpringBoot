package com.cfs.WeatherApp.service;

import com.cfs.WeatherApp.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService
{
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiURL;


    @Value("${weather.api.forecast.url}")
    private String apiforecastURL;

    public  String test()
    {
        return "test is woking";
    }

    private RestTemplate template =  new RestTemplate(); //differ API to consume we need this
    public WeatherResponse getData(String city)
    {
        String url=apiURL+"?key="+apiKey+"&q="+city;
        Root response =   template.getForObject(url,Root.class);
        WeatherResponse weatherResponse = new WeatherResponse();

        weatherResponse.setCity(response.getLocation().name);
        weatherResponse.setRegion(response.getLocation().region);
        weatherResponse.setCountry(response.getLocation().country);
        weatherResponse.setCondition( response.getCurrent().getCondition().getText() );
        weatherResponse.setTemperature( response.getCurrent().getTemp_c());

        return weatherResponse;
    }

    public WeatherForecast getForeCast(String city,int days)
    {
        WeatherForecast forcastnew = new WeatherForecast();
        WeatherResponse wr =  getData(city);
        WeatherForecast response =  new WeatherForecast();
        response.setWeatherResponse(wr);

        List<dayTemp> dayList =  new ArrayList<>();
        String url=apiforecastURL+"?key="+apiKey+"&q="+city+"&days="+days;
        Root apiResponse =   template.getForObject(url,Root.class);
        Forecast foreCast   =  apiResponse.getForecast();
        ArrayList<Forecastday> forecastdays = foreCast.getForecastday();
        for (Forecastday rs : forecastdays)
        {
            dayTemp day1 = new dayTemp();
            day1.setDate(rs.getDate());
            day1.setMinTemp(rs.getDay().mintemp_c);
            day1.setAvgTemp(rs.getDay().avgtemp_c);
            day1.setMaxTemp(rs.getDay().maxtemp_c);
            dayList.add(day1);
        }
        response.setDayTemp(dayList);
        return response ;
    }
}
