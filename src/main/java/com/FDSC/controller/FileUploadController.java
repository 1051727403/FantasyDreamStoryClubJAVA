package com.FDSC.controller;


import com.FDSC.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/upload")
public class FileUploadController {
    private static final String uploadPath = "D://image//";

    @PostMapping("/image")
    public Result uploadimg(@RequestParam MultipartFile photo) throws IOException {
        String  fileName = null;
        if(!photo.isEmpty()){

            String originalFilename = photo.getOriginalFilename();

            String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));

            fileName = UUID.randomUUID().toString()+suffixName;

            System.out.println(fileName);

            photo.transferTo(new File(uploadPath+fileName));
        }
        return Result.success(fileName);
    }


}
