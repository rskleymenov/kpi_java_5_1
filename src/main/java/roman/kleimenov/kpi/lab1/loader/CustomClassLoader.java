package roman.kleimenov.kpi.lab1.loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomClassLoader extends ClassLoader {

    public static final int BEGIN_INDEX = 17;
    public static final String REGEX = "/";
    public static final String REPLACEMENT = ".";
    public static final String CLASS = ".class";
    private String pathToClasses;
    private Set<String> localCache = new HashSet<>();

    public CustomClassLoader(String pathToClasses, ClassLoader parent) {
        super(parent);
        this.pathToClasses = pathToClasses;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte b[] = new LoaderUtils().fetchClassFromFS(pathToClasses + className + CLASS);
            String bytes = new String(b);
            String packagePath = pathToClasses
                    .substring(BEGIN_INDEX, pathToClasses.length())
                    .replaceAll(REGEX, REPLACEMENT);
            if (!localCache.contains(bytes)) {
                localCache.add(bytes);
                return defineClass(packagePath + className, b, 0, b.length);
            }
            return findLoadedClass(packagePath + className);
        } catch (FileNotFoundException ex) {
            return super.findClass(className);
        } catch (IOException ex) {
            return super.findClass(className);
        }

    }
}
