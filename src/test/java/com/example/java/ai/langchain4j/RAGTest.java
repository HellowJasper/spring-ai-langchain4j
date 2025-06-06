package com.example.java.ai.langchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;


@SpringBootTest
public class RAGTest {
    @Test
    public void testReadDocument() {
    //使用FileSystemDocumentLoader读取指定目录下的知识库文档
    //并使用默认的文档解析器TextDocumentParser对文档进行解析
//        Document document = FileSystemDocumentLoader.loadDocument("E:/knowledge/测试.txt");
//                    System.out.println(document.text());

//        // 加载单个文档
//        Document document1 = FileSystemDocumentLoader.loadDocument("E:/knowledge/file.txt", new TextDocumentParser());
//        System.out.println(document1);
//
//        //从一个目录中加载所有文档
//        List<Document> documents2 = FileSystemDocumentLoader.loadDocuments("E:/knowledge", new TextDocumentParser());
//        System.out.println(documents2);

        // 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.pdf");
        System.out.println(pathMatcher.matches(FileSystems.getDefault().getPath("E:/knowledge/file.txt")));
        List<Document> documents3 = FileSystemDocumentLoader.loadDocuments("E:/knowledge", pathMatcher, new TextDocumentParser());
        System.out.println(documents3);

//        // 从一个目录及其子目录中加载所有文档
//        List<Document> documents4 = FileSystemDocumentLoader.loadDocumentsRecursively("E:/knowledge", new TextDocumentParser());
//        System.out.println(documents4);
    }
    /**
     * 解析PDF
     */
    @Test
    public void testParsePDF() {
        Document document = FileSystemDocumentLoader.loadDocument("E:/knowledge/医院信息.pdf",
                new ApachePdfBoxDocumentParser()
        );
        System.out.println(document.metadata());
        System.out.println(document);
    }
}