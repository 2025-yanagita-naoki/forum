package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    /*
     * コメント編集画面表示処理
     */
    @GetMapping("/comEdit/{id}")
    public ModelAndView editComment(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集するコメントを取得
        CommentForm comment = commentService.editComment(id);
        // 画面遷移先を指定
        mav.setViewName("/commentEdit");
        // 準備した空のFormを保管
        mav.addObject("comments", comment);
        return mav;
    }

    /*
     * コメント編集処理
     */
    @PutMapping("/commentUpdate/{id}")
    public ModelAndView updateComment (@PathVariable Integer id,@ModelAttribute("comments") CommentForm comment) {
        // UrlParameterのidを更新するentityにセット
        comment.setId(id);
        // 編集した投稿を更新
        commentService.saveComment(comment);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント削除処理
     */
    @DeleteMapping("/comDelete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id){
        // 投稿をテーブルに格納
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

}
