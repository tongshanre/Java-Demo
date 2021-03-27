package com.tong.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class LuceneMain {
    public static void main(String[] args) throws IOException {
        Path path = new File("indexs_dir").toPath();
        FSDirectory fsDirectory = new MMapDirectory(path);
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter indexWriter = new IndexWriter(fsDirectory, config);
        Document doc = new Document();
        doc.add(new TextField("content", "hello world", Field.Store.NO));
        indexWriter.addDocument(doc);
        indexWriter.close();


    }
}
