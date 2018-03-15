package com.example.gaoyounan.weather_forecast.model;

import java.util.List;

/**
 * Created by gaoyounan on 2018/3/14.
 */

public class Weather_Info
{
    private String name;
    private List<Weather> weather;
    private Main main;
    private Clouds clouds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
}
