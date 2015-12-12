package com.example.joaquin.dijep_test;

/**
 * Created by joaquin on 12/12/2015.
 */
public class City {

    private String name;
    private String population;

    public City(String name, String population) {
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }
}
