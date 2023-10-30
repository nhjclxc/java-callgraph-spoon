package org.nhjclxc;

import org.nhjclxc.cg.SpoonCGProcessor;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        args = new String[]{"E:\\nbu\\other\\java-callgraph-spoon\\src\\main\\java\\org\\nhjclxc\\cg"};
        if (!(args.length > 0)){
            System.out.println("jar形式启动项目命令格式：java -jar java-callgraph-spoon-1.0-SNAPSHOT.jar file");
            System.out.println("file是单个*.java文件或是多个*.java文件列表，也可以是路径");
            System.out.println("example：");
            System.out.println("java -jar java-callgraph-spoon-1.0-SNAPSHOT.jar test.java");
            System.out.println("java -jar java-callgraph-spoon-1.0-SNAPSHOT.jar test.java test2.java test3.java");
            System.out.println("java -jar java-callgraph-spoon-1.0-SNAPSHOT.jar src\\main\\java\\org\\nhjclxc\\cg");
        }

        SpoonCGProcessor spoonCGProcessor = new SpoonCGProcessor();
        System.out.println("Parameter：");
        for (String arg : args) {
            System.out.println(arg);
            spoonCGProcessor.build(arg);
        }


    }
}