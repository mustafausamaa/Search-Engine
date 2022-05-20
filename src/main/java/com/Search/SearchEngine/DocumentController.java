package com.Search.SearchEngine;

import com.Search.SearchEngine.model.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
@RestController
public class DocumentController  {
    @Autowired
    private DocumentService documentService;
    @RequestMapping("/documents/{searchWord}")
    public List<Doc> getDocument(@PathVariable String searchWord) throws IOException {

        return documentService.getDocuments(searchWord);
    }
}