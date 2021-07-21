package com.fptu.capstone.service;

import com.fptu.capstone.entity.Chapter;
import com.fptu.capstone.entity.ChapterStatus;
import com.fptu.capstone.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ChapterRepository chapterRepository;

    public void publishChapters(int bookId) {
        List<Chapter> chapters = chapterRepository.findByBookIdAndChapterStatusId(bookId, 1);
        Date current = new Date();

        for(Chapter c: chapters) {
            Date publishedDate = c.getPublishDate();
            if(publishedDate != null && publishedDate.before(current)) {
                ChapterStatus status = new ChapterStatus();
                status.setId(2);
                c.setChapterStatus(status);
                chapterRepository.save(c);
            }
        }
    }
}
