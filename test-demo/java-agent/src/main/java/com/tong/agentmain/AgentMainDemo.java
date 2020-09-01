package com.tong.agentmain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class AgentMainDemo {
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("Agentmain call-----------------------------------");

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                System.out.println("agentmain load classs:" + className);
                if (className.endsWith("Dog")) {
                    try {
                        System.out.println("load bytes");
                        return getClassBytes(Dog.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return classfileBuffer;
            }
        }, true);
        inst.retransformClasses(Dog.class);
    }

    static byte[] getClassBytes(Class c) throws IOException {
        String path = c.getCanonicalName().replaceAll("\\.", "/") + ".class";
        System.out.println("getCLassBytes:" + path);
        final InputStream is = AttachMain.class.getClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = is.read(buffer)) > 0) {
            bos.write(buffer, 0, n);
        }
        return bos.toByteArray();
    }
}
