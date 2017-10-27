package g.service.webSocket.codeCreator;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class KFile {

    public static ArrayList<String> getClassNames(String packageName) throws IOException {
        ArrayList<String> cns = new ArrayList<String>();
        String pathName = packageName.replace('.', '/');
        Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(pathName);
        packageName = packageName + ".";
        while (dirs.hasMoreElements()) {
            URL url = (URL) dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                File dir = new File(filePath);
                getClassNames(cns, dir, packageName);
            } else if ("jar".equals(protocol)) {
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.endsWith(".class") && name.indexOf("$") == -1 && name.startsWith(pathName)) {
                        name = name.replace('/', '.');
                        name = name.substring(0, name.length() - 6);
                        cns.add(name);
                    }
                }
            }
        }
        return cns;
    }

    public static void getClassNames(ArrayList<String> cns, File dir, String packageName) {
        if (!dir.exists())
            return;
        File dirfiles[] = dir.listFiles();
        for (int i = 0; i < dirfiles.length; i++) {
            File file = dirfiles[i];
            if (file.isDirectory()) {
                getClassNames(cns, file, packageName + file.getName() + ".");
            } else if (file.getName().endsWith(".class")) {
                cns.add(packageName + file.getName().substring(0, file.getName().length() - 6));
            }
        }
    }

}
