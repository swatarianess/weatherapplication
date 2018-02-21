package com.stetal.assignment.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Stephen T. Adu
 * @version 0.0.1
 * @since 21/02/2018
 */
public class Weather implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -3421130752764333668L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Weather() {
    }

    /**
     *
     * @param id
     * @param description
     * @param main
     */
    public Weather(Integer id, String main, String description) {
        super();
        this.id = id;
        this.main = main;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Weather withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Weather withMain(String main) {
        this.main = main;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Weather withDescription(String description) {
        this.description = description;
        return this;
    }

}
