package org.nhjclxc.cg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件助手
 *
 * @author 罗贤超
 * @since 2023/10/30 16:34
 */
public class FileUtils {


    public static void writeDotFile(String outputPath, Map<String, SpoonCGNode> spoonCGNodeMap) {
        Map<String, String> methodInvocationDotGraph = toDot(spoonCGNodeMap);
        for (Map.Entry<String, String> entry : methodInvocationDotGraph.entrySet()) {
            String method = entry.getKey();
            String dot = entry.getValue();
            write(outputPath + "\\" + getFilePath(method) + ".dot", dot);
        }

    }

    private static String getFilePath(String methodQualifiedName) {
        // 使用正则表达式匹配第一个圆括号及其之前的所有数据
        Matcher matcher = Pattern.compile("^(.*?)\\(").matcher(methodQualifiedName);
        String path = matcher.find() ? matcher.group(1) : System.currentTimeMillis() + "";
        return path.replaceAll("\\.", "\\\\");
    }

    private static void write(String filename, String data) {
        try {
            File file = new File(filename);
            // 创建目录（如果不存在）
            File directory = file.getParentFile();
            if (!directory.exists()) {
                boolean mkdirs = directory.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回当前扫描到的类里面的所有方法调用dot格式图结构 <br> <br>
     *
     * <a href="https://www.graphviz.org/documentation">dot语言介绍</a> <br> <br>
     * <a href="https://dreampuf.github.io/GraphvizOnline">在线dot格式图可视化</a> <br> <br>
     * <a href="https://www.graphviz.org/pdf/dotguide.pdf">Drawing graphs with dot</a> <br> <br>
     *
     * @param spoonCGNodeMap 调用关系元素数据
     * @return 每个方法的dot调用关系
     * @author 罗贤超
     */
    public static Map<String, String> toDot(Map<String, SpoonCGNode> spoonCGNodeMap) {
        Map<String, String> graph = new HashMap<>();

        for (Map.Entry<String, SpoonCGNode> entry : spoonCGNodeMap.entrySet()) {
            String key = entry.getKey();
            SpoonCGNode root = entry.getValue();
            StringBuilder dot = new StringBuilder("digraph \"" + key + "\" {\n");
            List<String> edgeList = new LinkedList<>();
            buildEdges(root, edgeList);
            if (edgeList.size() > 0) {
                for (String s : edgeList) {
                    dot.append(s);
                }
            }
            dot.append("}");
            graph.put(key, String.valueOf(dot));
        }
        return graph;
    }

    /**
     * 构造从当前节点到终点的所有路径
     *
     * @param spoonCGNode 当前节点
     * @param edgeList 边集合
     * @author 罗贤超
     */
    private static void buildEdges(SpoonCGNode spoonCGNode, List<String> edgeList) {
        List<SpoonCGNode> spoonCGDotList = spoonCGNode.getSpoonCGDotList();
        if (spoonCGDotList != null && spoonCGDotList.size() > 0){
            String key = spoonCGNode.getMethodQualifiedName();
            for (SpoonCGNode cgNode : spoonCGDotList) {
                String edge = "\"" + key + "\" -> \"" + cgNode.getMethodQualifiedName();
                buildEdges(cgNode, edgeList);
                edge += "\";\n\n";
                edgeList.add(edge);
            }
        }
    }

}