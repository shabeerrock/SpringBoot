package com.cfs.WeatherApp.dto;

import java.util.ArrayList;
import java.util.List;

public class Forecast {
    public ArrayList<Forecastday> forecastday;

    public ArrayList<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(ArrayList<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }
}
