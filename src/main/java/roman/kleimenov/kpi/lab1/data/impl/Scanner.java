package roman.kleimenov.kpi.lab1.data.impl;

import roman.kleimenov.kpi.lab1.data.Device;

public class Scanner implements Device {
    @Override
    public void load() {
        System.out.println("Hi, I'm trying to loading :)");
    }

    @Override
    public void work() {
        System.out.println("SCAAAAAAAAAAAAAAAAAAAAAANIIIIIIIIIIIIIIIIIINNNNNNNNNNNNGGGGGGGGGGGG");
    }

    @Override
    public void unload() {
        System.out.println("I would like to get a little rest");
    }
}
