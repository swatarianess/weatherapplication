package com.stetal.assignment.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Stephen T. Adu
 * @version 0.0.1
 * @since 21/02/2018
 */
public class Sys implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;
    private final static long serialVersionUID = 8263218183942630218L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sys() {
    }

    /**
     *
     * @param id
     * @param sunset
     * @param sunrise
     * @param country
     */
    public Sys(Integer id, String country, Integer sunrise, Integer sunset) {
        super();
        this.id = id;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sys withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Sys withCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Sys withSunrise(Integer sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Sys withSunset(Integer sunset) {
        this.sunset = sunset;
        return this;
    }

}
