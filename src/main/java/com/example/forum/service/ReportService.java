package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport() {
        List<Report> results = reportRepository.findAllByOrderByIdDesc();
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreatedDate(result.getCreatedDate());
            report.setUpdatedDate(result.getUpdatedDate());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());

        LocalDateTime nowDate = LocalDateTime.now();
        report.setCreatedDate(nowDate);
        report.setUpdatedDate(nowDate);
        return report;
    }

    /*
     * レコード削除
     */
    public void deleteReport(Integer id) {
        reportRepository.deleteById(id);
    }

    /*
     * レコード編集
     */
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        results.add(reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);

    }

    public List<ReportForm> findDateReport(String startDate, String endDate) {
        String start;
        String end;
        if(!StringUtils.isEmpty(startDate)) {
            start = startDate + " 00:00:00";
        } else {
            start = "2020-01-01 00:00:00";
        }

        if(!StringUtils.isEmpty(endDate)) {
            end = endDate + " 23:59:59";
        } else {
            Calendar cl = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            end = sdf.format(cl.getTime());
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDateTime startFormat;
        try {
            startFormat = sdFormat.parse(start);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat edFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endFormat;
        try {
            endFormat = edFormat.parse(end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<Report> results = reportRepository.findByCreatedDateBetween(startFormat, endFormat);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
}

