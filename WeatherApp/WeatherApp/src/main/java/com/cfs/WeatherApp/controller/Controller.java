package com.cfs.WeatherApp.controller;

import com.cfs.WeatherApp.dto.Root;
import com.cfs.WeatherApp.dto.WeatherForecast;
import com.cfs.WeatherApp.dto.WeatherResponse;
import com.cfs.WeatherApp.dto.forcast;
import com.cfs.WeatherApp.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin
public class Controller
{
    @Autowired
    private  WeatherService weatherService;




    @GetMapping("/{city}")
    public String getWeatherDate(@PathVariable String city)
    {
        return weatherService.test();
    }

    @GetMapping("my/{city}")
    public WeatherResponse getWeather(@PathVariable String city)
    {
        return weatherService.getData(city);
    }

    @GetMapping("/forecast")
    public WeatherForecast getForecast(@RequestParam String city, @RequestParam int days)
    {
        return weatherService.getForeCast(city,days);
    }

}
