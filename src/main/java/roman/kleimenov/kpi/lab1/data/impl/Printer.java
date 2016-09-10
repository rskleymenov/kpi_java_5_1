package roman.kleimenov.kpi.lab1.data.impl;

import roman.kleimenov.kpi.lab1.data.Device;

public class Printer implements Device {

    @Override
    public void load() {
        System.out.println("Device " + this.getClass().getSimpleName() + " loading ...!!!!");
    }

    @Override
    public void work() {
        System.out.println("Device " + this.getClass().getSimpleName() + " working ...!!!!");
    }

    @Override
    public void unload() {
        System.out.println("Device " + this.getClass().getSimpleName() + " unloading ...!!!!");
    }
}