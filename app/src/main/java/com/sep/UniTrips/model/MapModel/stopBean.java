package com.sep.UniTrips.model.MapModel;

import android.location.Location;


public class stopBean {
    private String id;
    private String name;
    private String type;
    private Location location;

    public void setLocation(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return id;}

    public void setName(String name){this.name = name;}
    public String getName (){return name;}

    public void setType (String type){this.type = type;}
    public String getType(){return type;}

}

