package com.bodjo.main.objects;

import java.util.ArrayList;

public class Services {
    private ArrayList<Service> services;

    public Services(ArrayList<Service> services) {
        this.services = services;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
