package com.fptu.capstone.service;

import com.fptu.capstone.entity.ReportStatus;
import com.fptu.capstone.repository.ReportRepository;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ReportEditServiceImpl implements ReportEditService{
    private final ReportRepository reportRepository;

    public ReportEditServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public boolean response(String content, int id) {
        try {
            var report =  reportRepository.findById(id);
            if (report == null){
                return false;
            }
            report.setResponseDate(new Date());
            report.setResponseContent(content);
            var Status = new ReportStatus();
            Status.setStatusId(2);
            Status.setStatusName("Đã phản hổi");
            report.setStatusId(Status);
            reportRepository.save(report);
            return true;
        }
        catch (Exception e) {
            System.out.println("lỗi");
        }
        return false;
    }
}
