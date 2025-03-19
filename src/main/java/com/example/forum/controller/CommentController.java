package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;


    @GetMapping("/comment/{id}")
    public ModelAndView newComment(@PathVariable Integer id, @ModelAttribute("comments") CommentForm commentForm){
        commentForm.setId(0);
        commentForm.setReportId(id);
        // コメントをテーブルに格納
        commentService.saveComment(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/commentEdit")
    public ModelAndView editComment() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        CommentForm commentForm = new CommentForm();
        // 画面遷移先を指定
        mav.setViewName("/commentEdit");
        // 準備した空のFormを保管
        mav.addObject("comments", commentForm);
        return mav;
    }
}
