package roman.kleimenov.kpi.lab1.loader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomLoader {

    private String pathToClasses;
    private ClassLoader parent;
    private Map<String, String> localClassCache = new HashMap<>();
    private CustomClassLoader loader = new CustomClassLoader(pathToClasses, parent);

    public CustomLoader(String pathToClasses, ClassLoader parent) {
        this.parent = parent;
        this.pathToClasses = pathToClasses;
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            byte[] b = new LoaderUtils().fetchClassFromFS(pathToClasses + name + ".class");
            String bytes = new String(b);
            if (!bytes.equalsIgnoreCase(localClassCache.get(name))) {
                localClassCache.put(name, bytes);
                loader = new CustomClassLoader(pathToClasses, parent);
                return loader.loadClass(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader.findClass(name);
    }

    public void clear() {
        loader.clearAssertionStatus();
    }

}
