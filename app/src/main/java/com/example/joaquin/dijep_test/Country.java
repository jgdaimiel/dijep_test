package com.example.joaquin.dijep_test;

/**
 * Created by joaquin on 12/12/2015.
 */
public class Country {

    private String name;
    private String flagName;

    public Country(String name, String flagName) {
        super();
        this.name = name;
        this.flagName = flagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
}
