package com.stetal.assignment.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * @author Stephen T. Adu
 * @version 0.0.1
 * @since 21/02/2018
 */

public class Main implements Serializable
{

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;
    private final static long serialVersionUID = 1999449675925964440L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Main() {
    }

    /**
     *
     * @param humidity The humidity of the location
     * @param pressure The pressure of the location
     * @param temp The temp of the location
     * @param tempMin The temp_min of the location
     * @param tempMax The temp max of the location
     */
    public Main(Double temp, Integer pressure, Integer humidity, Double tempMin, Double tempMax) {
        super();
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Main withTemp(Double temp) {
        this.temp = temp;
        return this;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Main withPressure(Integer pressure) {
        this.pressure = pressure;
        return this;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Main withHumidity(Integer humidity) {
        this.humidity = humidity;
        return this;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Main withTempMin(Double tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Main withTempMax(Double tempMax) {
        this.tempMax = tempMax;
        return this;
    }

}
