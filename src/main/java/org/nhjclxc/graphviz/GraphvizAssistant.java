package org.nhjclxc.graphviz;

import java.io.*;

/**
 * Graphviz图片生成助手 <br><br>
 *
 * @author LuoXianchao
 * @since 2023/10/30 23:36
 */
public class GraphvizAssistant {

    /**
     * The image size in dpi. 96 dpi is normal size. Higher values are 10% higher each. Lower values
     * 10% lower each.
     * <p>
     * dpi patch by Peter Mueller
     */
    private static final int[] dpiSizes = {46, 51, 57, 63, 70, 78, 86, 96, 106, 116, 128, 141, 155, 170, 187, 206, 226, 249};

    /**
     * Define the index in the image size array.
     */
    private static final int currentDpiPos = 14;

    /**
     * Graphviz软件安装路径
     */
    private static final String executable = "D:\\develop\\Graphviz\\bin\\dot.exe";


    /**
     * 从dot语言生成image
     * It will call the external dot program, and return the image in binary format.
     *
     * @param dotFile            Source of the graph (in dot language).
     * @param imageType          Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @param representationType Type of how you want to represent the graph:
     *                           <ul>
     *                           <li>dot</li>
     *                           <li>neato</li>
     *                           <li>fdp</li>
     *                           <li>sfdp</li>
     *                           <li>twopi</li>
     *                           <li>circo</li>
     *                           </ul>
     * @author 罗贤超
     */
    public static void createImage(final File dotFile, final String imageType, final String representationType) {
        try {
            String filePathPerfix = getFilePathPerfix(dotFile.getAbsolutePath());
            File imageFile = new File(filePathPerfix + "." + imageType);

            final Runtime rt = Runtime.getRuntime();
            final String[] args = {executable, "-T" + imageType, "-K" + representationType, "-Gdpi=" + dpiSizes[currentDpiPos],
                    dotFile.getAbsolutePath(), "-o", imageFile.getAbsolutePath()};
            final Process process = rt.exec(args);
            process.waitFor();

//            System.out.println("文件写入成功");
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取文件路径前缀：path\filename，没有扩展名
     */
    private static String getFilePathPerfix(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        }
        return "";
    }

}
