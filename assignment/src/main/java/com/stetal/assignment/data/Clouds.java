package com.stetal.assignment.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Stephen T. Adu
 * @version 0.0.1
 * @since 21/02/2018
 */
public class Clouds implements Serializable
{

    @SerializedName("all")
    @Expose
    private Integer all;
    private final static long serialVersionUID = 8837095692303090212L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Clouds() {
    }

    /**
     *
     * @param all
     */
    public Clouds(Integer all) {
        super();
        this.all = all;
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public Clouds withAll(Integer all) {
        this.all = all;
        return this;
    }

}
