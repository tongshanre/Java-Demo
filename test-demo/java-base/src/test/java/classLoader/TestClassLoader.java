package classLoader;

import classLoader.lib.LoadOne;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;

public class TestClassLoader {
    class MyClassLoader extends ClassLoader {
        String basePath = MyClassLoader.getSystemClassLoader().getResource("").getPath();

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            Class<?> clazz = findClass(name);
            if (clazz != null) {
                return clazz;
            }
            return super.loadClass(name, resolve);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String tmpName = name;
            String path = basePath + tmpName.replaceAll("\\.", Matcher.quoteReplacement(File.separator)) + ".class";
            try {
                File file = new File(path);
                if (file.exists()) {
                    byte[] buff = new byte[1024 * 4];
                    InputStream is = new FileInputStream(file);
                    int n = is.read(buff);
                    return defineClass(name, buff, 0, n);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public void testClassLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException {

        MyClassLoader classLoader = new MyClassLoader();
        this.getClass().getClassLoader();
        System.out.println(classLoader.getParent());
        while (true) {
            //Class<?> classOne = Thread.class.getClassLoader().loadClass("classLoader.lib.LoadOne");
            //Object loadOne = classOne.newInstance();
            //Method say = loadOne.getClass().getMethod("say");
            //say.invoke(loadOne);
            LoadOne loadOne = new LoadOne();
            loadOne.say();
            Thread.sleep(1000 * 5);
            //classLoader = new MyClassLoader();
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InterruptedException {
        new TestClassLoader().testClassLoader();
    }
}
