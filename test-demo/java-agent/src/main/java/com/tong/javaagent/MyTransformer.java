package com.tong.javaagent;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 处理Person类
        if (className.equals("com/tong/javaagent/Person")) {
            ClassPool classPool = ClassPool.getDefault();
            try {
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod ctMethod = ctClass.getDeclaredMethod("eat");
                ctMethod.insertBefore("System.out.println(\"饭前请洗手\");");
                ctMethod.insertAfter("System.out.println(\"饭后请擦嘴\");");
                return ctClass.toBytecode();
            } catch (IOException | NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}
