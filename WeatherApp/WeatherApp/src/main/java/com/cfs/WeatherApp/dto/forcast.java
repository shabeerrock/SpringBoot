package com.cfs.WeatherApp.dto;

import java.util.List;

public class forcast
{
    private  WeatherResponse weatherResponse;

    private List<dayTemp> dateTemp;

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public List<dayTemp> getDateTemp() {
        return dateTemp;
    }

    public void setDateTemp(List<dayTemp> dateTemp) {
        this.dateTemp = dateTemp;
    }
}
