package com.minjer.controller;

import com.minjer.pojo.Result;
import com.minjer.service.SentenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Minjer
 */
@Slf4j
@RestController
public class SentenceController {

    @Autowired
    private SentenceService sentenceService;

    /**
     * 快速感知评论信息
     *
     * @param comments 评论信息
     *                 {
     *                 "comments": [
     *                 "这个视频真的很好看",
     *                 "这个视频真的很难看"
     *                 ]
     *                 }
     * @return 含数据的结果
     */
    @RequestMapping(value = "/api/v1/sentence", method = RequestMethod.POST)
    public Result handleSentences(@RequestBody Map<String, List<String>> comments) {
        log.info("handleSentences: " + comments.toString());
        ArrayList<String> sentences = (ArrayList<String>) comments.get("comments");
        Result result = sentenceService.handleSentences(sentences);
        log.info("handleSentences result: " + result.toString());
        return result;
    }
}
