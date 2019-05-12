package uk.me.pilgrim.jcore.ecs.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import org.checkerframework.checker.units.qual.C;
import uk.me.pilgrim.jcore.ecs.Component;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class ComponentProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<Element> components = getComponents(roundEnv);
        if (components.size() == 0) return false;

        TypeSpec.Builder builder = TypeSpec.enumBuilder("Components");
        builder.addModifiers(Modifier.PUBLIC);

        for (Element c : components){
            builder.addEnumConstant(c.getSimpleName().toString(), TypeSpec.anonymousClassBuilder("$T.class", c).build());
        }


        ParameterizedTypeName classType = ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(Component.class));


        builder.addField(classType, "type", Modifier.PRIVATE, Modifier.FINAL);
        builder.addMethod(MethodSpec.constructorBuilder().addParameter(classType, "type").addStatement("this.$N = $N", "type", "type").build());
        builder.addMethod(MethodSpec.methodBuilder("getType").returns(classType).addStatement("return this.type").build());

        JavaFile javaFile = JavaFile.builder("uk.me.pilgrim.jcore.ecs", builder.build()).build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private List<Element> getComponents(RoundEnvironment roundEnv){
        Set<? extends Element> elements = roundEnv.getRootElements();
        TypeMirror component = processingEnv.getElementUtils().getTypeElement("uk.me.pilgrim.jcore.ecs.Component").asType();
        return elements
            .parallelStream()
            .filter(el -> el.getKind().isClass())
            .filter(el -> processingEnv.getTypeUtils().directSupertypes(el.asType()).contains(component))
            .collect(Collectors.toList());
    }

    private void error(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void error(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void note(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }
}
