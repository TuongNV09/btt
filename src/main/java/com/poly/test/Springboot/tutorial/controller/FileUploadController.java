package com.poly.test.Springboot.tutorial.controller;

import com.poly.test.Springboot.tutorial.models.ResponseObject;
import com.poly.test.Springboot.tutorial.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    //Inject Storage Service here
    @Autowired
    private IStorageService storageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> fileUpload(@RequestParam("file")MultipartFile file){
        try {
            // save file to a folder ==> use a service

        }catch (Exception exception){

        }
    }
}
