package com.tong.apt;

import com.google.auto.service.AutoService;
import com.squareup.javawriter.JavaWriter;
import com.tong.anno.TestAnnotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


@AutoService(Processor.class)
public class MyAPT extends AbstractProcessor {

    private Filer filter;
    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filter = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<String>();
        types.add(TestAnnotation.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(TestAnnotation.class);
        for (Element element : elements) {
            messager.printMessage(Diagnostic.Kind.NOTE, element.toString());
            TypeElement typeElement = (TypeElement) element;
            try {
                JavaFileObject jsf = filter.createSourceFile(typeElement.getQualifiedName(), typeElement);
                JavaWriter jw = new JavaWriter(jsf.openWriter());
                jw.emitPackage("com.tong.service");
                jw.beginType(typeElement.getQualifiedName().toString(), "class", EnumSet.of(Modifier.PUBLIC));

                jw.endType();
                jw.close();
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        return true;
    }
}
