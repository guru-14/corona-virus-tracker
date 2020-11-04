package com.project.coronavirustracker.models;

public class CoronaVirusStats {

    private String state;
    private String country;
    private int numberOfCases;
    private String formattedNumberOfCases;
    private int numberOfNewCases;
    private String formattedNumberOfNewCases;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberOfCases() {
        return numberOfCases;
    }

    public void setNumberOfCases(int numberOfCases) {
        this.numberOfCases = numberOfCases;
    }

    public String getFormattedNumberOfCases() {
        return formattedNumberOfCases;
    }

    public void setFormattedNumberOfCases(String formattedNumberOfCases) {
        this.formattedNumberOfCases = formattedNumberOfCases;
    }

    public int getNumberOfNewCases() {
        return numberOfNewCases;
    }

    public void setNumberOfNewCases(int numberOfNewCases) {
        this.numberOfNewCases = numberOfNewCases;
    }

    public String getFormattedNumberOfNewCases() {
        return formattedNumberOfNewCases;
    }

    public void setFormattedNumberOfNewCases(String formattedNumberOfNewCases) {
        this.formattedNumberOfNewCases = formattedNumberOfNewCases;
    }
}