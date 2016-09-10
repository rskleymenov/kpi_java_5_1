package roman.kleimenov.kpi.lab1;

import roman.kleimenov.kpi.lab1.data.Device;
import roman.kleimenov.kpi.lab1.loader.CustomLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Runner {

    public static final String TARGET_COMPILED_CLASSES = "./target/classes/roman/kleimenov/kpi/lab1/data/impl/";

    public static void main(String args[]) throws Exception{
        CustomLoader loader = getCustomLoader();
        loadClassesFromCustomDirectory(loader);
    }

    private static CustomLoader getCustomLoader() {
        return new CustomLoader(TARGET_COMPILED_CLASSES, ClassLoader.getSystemClassLoader());
    }

    private static void loadClassesFromCustomDirectory(CustomLoader loader) throws Exception {
        File dir = new File(TARGET_COMPILED_CLASSES);
        while (true) {
            String[] modules = dir.list();
            if (modules == null) continue;
            for (String module : modules) {
                String moduleName = module.split(".class")[0];
                Class clazz = loader.loadClass(moduleName);
                Device execute = (Device) clazz.newInstance();

                execute.load();
                execute.work();
                execute.unload();

                System.out.println(" ========================= ");
                Thread.sleep(1000L);
                loader.clear();
            }
        }
    }
}
