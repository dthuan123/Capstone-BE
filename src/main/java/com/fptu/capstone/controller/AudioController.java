package com.fptu.capstone.controller;


import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.repository.ChapterRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@CrossOrigin
@RestController
public class AudioController {

    @Autowired
    AudioService audioService;

    @Autowired
    ChapterRepository chapterRepository;

    @ResponseBody
    @GetMapping("/audio")
    public void  getAudio(@RequestParam int chapterId, @RequestParam boolean female, HttpServletResponse response) {
        try (OutputStream out = new FileOutputStream("output.mp3")) {
            out.write(audioService.getAudio(chapterId, female));
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
            System.out.println("Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
