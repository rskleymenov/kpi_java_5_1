package roman.kleimenov.kpi.lab1.loader;

import roman.kleimenov.kpi.lab1.data.Device;

import java.io.*;

public class LoaderUtils {

    private static final String WAITING_MESSAGE = "\nWaiting for recompiled file....... \n";
    private static final String LINE = " ========================= ";
    private static final String TOO_LARGE_FILE = "Too large file";
    private static final String COULD_NOT_COMPLETELY_READ_FILE = "Could not completely read file ";
    private static final long SLEEP_TIME = 250L;
    private static final long OUTPUT_SLEEP_TIME = 1000L;

    public byte[] fetchClassFromFolder(String path) throws IOException {
        InputStream stream = null;
        File file = new File(path);
        long length = file.length();
        byte[] bytes = new byte[Math.toIntExact(length)];
        int offset = 0;
        int inputBytes;
        boolean notFound;
        do {
            try {
                stream = new FileInputStream(file);
                notFound = false;
            } catch (FileNotFoundException e) {
                System.out.println(WAITING_MESSAGE);
                System.out.println(LINE);
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                notFound = true;
            }
        } while (notFound);

        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(TOO_LARGE_FILE);
        }

        while (offset < bytes.length
                && (inputBytes = stream.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += inputBytes;
        }

        if (offset < bytes.length) {
            throw new IOException(COULD_NOT_COMPLETELY_READ_FILE + path);
        }
        stream.close();
        return bytes;
    }

    public static CustomLoader getCustomLoader(String target) {
        return new CustomLoader(target, ClassLoader.getSystemClassLoader());
    }

    public static void loadClassesFromCustomDirectory(CustomLoader loader, String target) throws Exception {
        File dir = new File(target);
        while (true) {
            String[] devices = dir.list();
            if (devices == null) continue;
            for (String device : devices) {
                process(loader, device);
                System.out.println(LINE);
                Thread.sleep(OUTPUT_SLEEP_TIME);
            }
        }
    }

    private static void process(CustomLoader loader, String moduleName) throws Exception {
        moduleName = moduleName.split(".class")[0];
        Class clazz = loader.loadClass(moduleName);
        Device execute = (Device) clazz.newInstance();
        execute.load();
        execute.work();
        execute.unload();
    }
}
