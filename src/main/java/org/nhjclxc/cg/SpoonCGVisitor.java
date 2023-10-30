package org.nhjclxc.cg;

import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.CtScanner;

import java.util.*;

/**
 * Spoon抽象语法树访问者
 *
 * @author 罗贤超
 * @since 2023/10/30 11:34
 */
public class SpoonCGVisitor extends CtScanner {

    /**
     * <方法完全限定名, 解析到的方法对象>
     */
    private final Map<String, SpoonCGNode> spoonCGNodeMap = new HashMap<>();
    /**
     * 已经访问过的对象
     */
    private final Set<String> visitedMethodSet = new HashSet<>();

    protected Map<String, SpoonCGNode> getSpoonCGNodeMap() {
        // 移除系统对象
        spoonCGNodeMap.entrySet().removeIf(entry -> !visitedMethodSet.contains(entry.getKey()));
        return spoonCGNodeMap;
    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> ctMethod) {
        if (ctMethod.getModifiers().contains(ModifierKind.ABSTRACT)) {
            return;
        }
        String methodQualifiedName = getMethodQualifiedName(ctMethod);
//        System.out.println("methodQualifiedName: " + methodQualifiedName);
        if (visitedMethodSet.contains(methodQualifiedName)) {
            return;
        }
        visitedMethodSet.add(methodQualifiedName);


        SpoonCGNode spoonCGNode = getSpoonCGNode(methodQualifiedName);
        List<SpoonCGNode> spoonCGNodeList = spoonCGNode.getSpoonCGDotList();

        // 处理方法
        for (CtElement element : ctMethod.getBody().getElements(null)) {
            String invocationedMethodQualifiedName = null;
            if (element instanceof CtConstructor) {
                CtConstructor<?> ctConstructor = (CtConstructor<?>) element;
                System.out.println("CtConstructor");
            }
            if (element instanceof CtInvocation) {
                CtInvocation<?> ctInvocation = (CtInvocation<?>) element;
                CtExecutableReference<?> executable = ctInvocation.getExecutable();
                String qualifiedName = null;
                try {
                    qualifiedName = executable.getDeclaringType().getQualifiedName();
                } catch (Exception ignored) {
                }
                if (qualifiedName == null) {
                    qualifiedName = executable.getType().toString();
                }
                invocationedMethodQualifiedName = qualifiedName + "." + executable;
            } else if (element instanceof CtConstructor) {
                CtConstructor<?> ctConstructor = (CtConstructor<?>) element;
                System.out.println("CtConstructor");
            }
            if (element instanceof CtConstructorCall) {
                CtExecutableReference<?> executable = ((CtConstructorCall<?>) element).getExecutable();
                invocationedMethodQualifiedName = executable.toString();
            } else if (element instanceof CtExecutableReference) {
//                CtExecutableReference<?> ctExecutableReference = (CtExecutableReference<?>) element;
//                invocationedMethodQualifiedName = ctExecutableReference.getDeclaringType().getQualifiedName() + "." + ctExecutableReference;
            } else if (element instanceof CtCodeSnippetExpression) {
                CtCodeSnippetExpression<?> ctCodeSnippetExpression = (CtCodeSnippetExpression<?>) element;
                System.out.println("CtCodeSnippetExpression");
            }

            if (invocationedMethodQualifiedName == null) continue;

//            invocationedMethodQualifiedNameList.add(invocationedMethodQualifiedName);

            SpoonCGNode invocationedSpoonCGNode = getSpoonCGNode(invocationedMethodQualifiedName);
            // 存入当前方法的调用列表
            spoonCGNodeList.add(invocationedSpoonCGNode);
        }

    }

    /**
     * 获取已经解析的对象
     *
     * @param methodQualifiedName 方法完全限定名
     * @return cg节点对象
     * @author 罗贤超
     */
    private SpoonCGNode getSpoonCGNode(String methodQualifiedName) {
        SpoonCGNode invocationedSpoonCGNode = spoonCGNodeMap.get(methodQualifiedName);
        if (invocationedSpoonCGNode == null) {
            invocationedSpoonCGNode = new SpoonCGNode(methodQualifiedName);
            spoonCGNodeMap.put(methodQualifiedName, invocationedSpoonCGNode);
        }
        return invocationedSpoonCGNode;
    }

    /**
     * 获取方法完全限定名 <br><br>
     * package.class.method(parmaterList): returnType <br>
     *
     * @param method 方法节点
     * @param <T>    节点泛型
     * @return package.class.method(parmaterList): returnType
     * @author 罗贤超
     */
    private <T> String getMethodQualifiedName(CtMethod<T> method) {
        // 获取声明该方法的类
        CtType<?> declaringType = method.getDeclaringType();

        // 获取包名
        String packageName = declaringType.getPackage().getQualifiedName();
//            System.out.println("Package: " + packageName);

        // 获取类名
        String className = declaringType.getSimpleName();
//            System.out.println("Class Name: " + className);

        // 方法名
        String methodName = method.getSignature();
//            System.out.println("Method: " + methodName);

        // 返回值类型
        String returnType = method.getType().getQualifiedName();
//            System.out.println("returnType: " + returnType);

        // 方法修饰符
//            String methodModifier = getMethodModifier(method.getModifiers());

        return packageName + "." + className + "." + methodName;// + ": " + returnType;
    }

    /**
     * 获取方法修饰符
     *
     * @param modifiers 修饰符列表
     * @return 修饰符
     * @author 罗贤超
     */
    public String getMethodModifier(Set<ModifierKind> modifiers) {
        String methodModifier = "";
        if (modifiers.contains(ModifierKind.PUBLIC)) {
            methodModifier += ModifierKind.PUBLIC + " ";
        }
        if (modifiers.contains(ModifierKind.PROTECTED)) {
            methodModifier += ModifierKind.PROTECTED + " ";
        }
        if (modifiers.contains(ModifierKind.PRIVATE)) {
            methodModifier += ModifierKind.PROTECTED + " ";
        }
        if (modifiers.contains(ModifierKind.ABSTRACT)) {
            methodModifier += ModifierKind.ABSTRACT + " ";
        }
        if (modifiers.contains(ModifierKind.STATIC)) {
            methodModifier += ModifierKind.STATIC + " ";
        }
        if (modifiers.contains(ModifierKind.FINAL)) {
            methodModifier += ModifierKind.FINAL + " ";
        }
        if (modifiers.contains(ModifierKind.SYNCHRONIZED)) {
            methodModifier += ModifierKind.SYNCHRONIZED + " ";
        }
        if (modifiers.contains(ModifierKind.NATIVE)) {
            methodModifier += ModifierKind.SYNCHRONIZED + " ";
        }
        if (modifiers.contains(ModifierKind.STRICTFP)) {
            methodModifier += ModifierKind.STRICTFP + " ";
        }
        if (modifiers.contains(ModifierKind.TRANSIENT)) {
            methodModifier += ModifierKind.TRANSIENT + " ";
        }
        if (modifiers.contains(ModifierKind.VOLATILE)) {
            methodModifier += ModifierKind.VOLATILE + " ";
        }
        return methodModifier;
    }
}