package com.cfs.WeatherApp.dto;

import java.util.List;

public class WeatherForecast {
    private WeatherResponse weatherResponse;
    private List<dayTemp> dayTemp;



    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public List<dayTemp> getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(List<dayTemp> dayTemp) {
        this.dayTemp = dayTemp;
    }
}
