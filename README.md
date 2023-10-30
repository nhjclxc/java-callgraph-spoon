  java-callgraph-spoon
=========================================

* 使用静态分析工具spoon构造方法调用图callgraph，Use spoon build method to call graph cg
* 从java源代码生成java调用图，generate a java call graph from java source code
* 


##  keywords：java、callgraph、java-callgraph、cg、static analysis、source code analysis


##  run

### download
    `git clone https://github.com/nhjclxc/java-callgraph-spoon.git`
    

### 源代码方式启动 source code
    导入idea的maven项目，进入 `org.nhjclxc.cg.SpoonCGProcessor.main()` ，在里面指定你要解析的java文件或是目录，之后直接启动，然后在项目顶级会出现一个`output`文件夹，dot格式的文件就在里面


### jar方式启动 java -jar

  -  `cd java-callgraph-spoon`
  -  `mvn clean package`
  -  `cd target`
  -  `java -jar java-callgraph-spoon-1.0-SNAPSHOT-jar-with-dependencies.jar path`，
        - 如 `java -jar java-callgraph-spoon-1.0-SNAPSHOT-jar-with-dependencies.jar "E:\nbu\other\java-callgraph-spoon\src\main\java\org\nhjclxc"`
  - 随后在jar包目录下会生成一个 `output` 文件夹，生成的dot格式调用图就在里面

### 结果示例

[build.dot](output%2Forg%2Fnhjclxc%2Fcg%2FSpoonCGProcessor%2Fbuild.dot)
![build.png](output%2Forg%2Fnhjclxc%2Fcg%2FSpoonCGProcessor%2Fbuild.png)
![build.svg](output%2Forg%2Fnhjclxc%2Fcg%2FSpoonCGProcessor%2Fbuild.svg)


## 可视化

- 访问 [Graphviz Online](https://dreampuf.github.io/GraphvizOnline)
- 后期将尝试通过项目直接调用Graphviz工具生成图片，现在拿到dot的图结构其实就可以在这个基础上进行调用图的分析工作了，比如图相似性、向量化等等


## 注意
- 如果提供的知识一个文件的话，只会分析该文件内部的方法调用。因此，建议将你要分析的java文件的import里面包含的所有文件同时提供，否之生成的调用图只是单文件的（如果闲提供文件列表太繁琐，你可以把相关文件放到一个文件夹里面，提供文件夹路径即可访问下面的所有文件，构建致谢文件之间函数的调用关系）

- 项目现在可能还不够完善，可能还存在一些分析不足的问题，后续将会补充完善


## Author

-  [LuoXianchao](https://github.com/nhjclxc) 
- <time>October 30, 2023, 6:35 PM</time>.


## License

- [Apache-2.0 license](https://www.apache.org/licenses/LICENSE-2.0)
- <a href="https://github.com/nhjclxc/java-callgraph-spoon/blob/main/LICENSE" target="_blank">项目 Apache-2.0 license 许可证</a>

