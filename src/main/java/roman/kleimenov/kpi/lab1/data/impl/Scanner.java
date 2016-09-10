package roman.kleimenov.kpi.lab1.data.impl;

import roman.kleimenov.kpi.lab1.data.Device;

public class Scanner implements Device {

    @Override
    public void load() {
        System.out.println(this.getClass().getSimpleName() + ": Hi, I'm trying to loading :)");
    }

    @Override
    public void work() {
        System.out.println(this.getClass().getSimpleName() + ": I'm scanning...");
    }

    @Override
    public void unload() {
        System.out.println(this.getClass().getSimpleName() + ": I'm going to sleep...");
    }
}
