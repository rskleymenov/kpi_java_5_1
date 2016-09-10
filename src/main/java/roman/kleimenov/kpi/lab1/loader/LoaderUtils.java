package roman.kleimenov.kpi.lab1.loader;

import java.io.*;

public class LoaderUtils {
    public byte[] fetchClassFromFS(String path) throws IOException {
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
                System.out.println("\nWaiting for recompiled file....... \n");
                System.out.println(" ========================= ");
                try {
                    Thread.sleep(250L);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                notFound = true;
            }
        } while (notFound);

        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Too large file");
        }

        while (offset < bytes.length
                && (inputBytes = stream.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += inputBytes;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + path);
        }
        stream.close();
        return bytes;
    }
}
