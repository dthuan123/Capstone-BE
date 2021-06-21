package com.fptu.capstone.controller;


import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@CrossOrigin
@RestController
public class AudioController {

    @Autowired
    AudioService audioService;

    @ResponseBody
    @GetMapping("/audio")
    public void  getAudio(HttpServletResponse response) {
//        InputStream inputStream = new ByteArrayInputStream(audioService.getAudio());
//
//        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Content-Disposition:", "attachment;filename=" + "audio.mp3");
//        responseHeaders.set("Content-Type:", "audio/mpeg");

        try (OutputStream out = new FileOutputStream("output.mp3")) {
            out.write(audioService.getAudio());
            //System.out.println("Audio content written to file \"output.mp3\"");
            File file = new File("output.mp3");
            response.setContentType("audio/mpeg");
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Length", file.length() + "");
            response.addHeader("Content-Range", "bytes " + "0-" + file.length() + "/" + file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "song.mp3" + "\"");
            response.setContentLength((int) file.length());
            OutputStream responseOutput = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            in.transferTo(responseOutput);
            out.close();
            in.close();
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                //.contentLength(file.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(inputStreamResource);
        //return new ResponseEntity(inputStreamResource, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/audio/success")
    public String audioSuccess() {
        return "OK";
    }
}
