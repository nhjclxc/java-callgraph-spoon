package org.nhjclxc.cg;

import spoon.Launcher;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

import java.util.Map;


/**
 * Spoon处理器
 *
 * @author 罗贤超
 * @since 2023/10/30 11:34
 */
public class SpoonCGProcessor extends AbstractProcessor<CtMethod<?>> { // 泛型CtMethod 表示对方法级别的处理

    private final SpoonCGVisitor spoonCGVisitor = new SpoonCGVisitor();

    @Override
    public void process(CtMethod element) {
        element.accept(spoonCGVisitor);
    }

    public void build(String path) {

        Launcher launcher = new Launcher();

        // 设置源码目录
        launcher.addInputResource(path);
        // 设置输出目录（可选）  该输出就是你对代码修改过后的结果，如果你只是对代码进行读取操作那么不需要设备该目录
        String outputPath = "output";
//        launcher.setSourceOutputDirectory(outputPath);

//         添加自定义Processor (MySpoonProcessor)
        launcher.addProcessor(this);

        // 运行Spoon
        launcher.run();

        // 获取方法节点数据
        Map<String, SpoonCGNode> spoonCGNodeMap = this.spoonCGVisitor.getSpoonCGNodeMap();

        FileUtils.writeDotFile(outputPath, spoonCGNodeMap);

        System.out.println("");
        System.out.println("解析成功，文件已生成，请在output文件夹下查收");
        System.out.println("");


    }

    public static void main(String[] args) {

        // path可以是文件夹或是单个文件
        String path = "E:\\nbu\\other\\java-callgraph-spoon\\src\\main\\java\\org\\nhjclxc";
        SpoonCGProcessor spoonCGProcessor = new SpoonCGProcessor();
        spoonCGProcessor.build(path);

    }
}