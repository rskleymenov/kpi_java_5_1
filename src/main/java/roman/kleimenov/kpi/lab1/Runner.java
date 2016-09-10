package roman.kleimenov.kpi.lab1;

import roman.kleimenov.kpi.lab1.loader.CustomLoader;

import static roman.kleimenov.kpi.lab1.loader.LoaderUtils.getCustomLoader;
import static roman.kleimenov.kpi.lab1.loader.LoaderUtils.loadClassesFromCustomDirectory;

public class Runner {

    private static final String TARGET_COMPILED_CLASSES = "./target/classes/roman/kleimenov/kpi/lab1/data/impl/";

    public static void main(String args[]) throws Exception {
        CustomLoader loader = getCustomLoader(TARGET_COMPILED_CLASSES);
        loadClassesFromCustomDirectory(loader, TARGET_COMPILED_CLASSES);
    }
}
