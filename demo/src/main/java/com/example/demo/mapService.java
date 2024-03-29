package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface mapService {
    void init();
    void submit(MultipartFile file);
    String nbOcc(String word);
}
