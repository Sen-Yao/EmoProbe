package com.minjer.controller;

import com.minjer.pojo.Filter;
import com.minjer.pojo.Result;
import com.minjer.pojo.VideoComment;
import com.minjer.service.CommentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论信息控制器
 *
 * @author Minjer
 * @version 1.0
 */
@Slf4j
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 感知由用户上传的评论信息
     *
     * @param videoComment 视频评论信息
     * @return 进行完情感分析的结果
     */
    @RequestMapping(value = "/api/v1/comments", method = RequestMethod.POST)
    public Result addComments(@RequestBody VideoComment videoComment) {
        log.info("addComments: " + videoComment.toString());
        Result result = commentService.handleComments(videoComment);
        log.info("addComments result: " + result.toString());
        return result;
    }


    /**
     * 获取筛选后的评论信息
     *
     * @param bv         筛选视频bv号
     * @param autopost   若未找到是否进行爬取
     * @param commentNum 每页评论数
     * @param pageIndex  页码
     * @param ip         被筛选的ip地址
     * @param sex        被筛选的性别
     * @param date       被筛选的日期
     * @param like       被筛选的点赞数
     * @param reply      被筛选的回复数
     * @param emotion    被筛选的情感
     * @return 筛选后的评论信息
     */
    @RequestMapping(value = "/api/v1/videos/page/filter", method = RequestMethod.GET)
    public Result getFilteredComments(
            @RequestParam("bv") String bv,
            @RequestParam("autopost") Integer autopost,
            @RequestParam("n_per_page") Integer commentNum,
            @RequestParam("pn") Integer pageIndex,
            @RequestParam(value = "user_ip", required = false) String ip,
            @RequestParam(value = "user_sex", required = false) String sex,
            @RequestParam(value = "comment_date", required = false) String date,
            @RequestParam(value = "comment_like", required = false) String like,
            @RequestParam(value = "comment_reply", required = false) String reply,
            @RequestParam(value = "emotion", required = false) String emotion
    ) {
        Filter filter = new Filter(bv, autopost, commentNum, pageIndex, ip, sex, date, like, reply, emotion);
        log.info("getFilteredComments: " + filter);
        Result result = commentService.filterComments(filter);
        log.info("getFilteredComments result : " + result.toString());
        return result;
    }
}
