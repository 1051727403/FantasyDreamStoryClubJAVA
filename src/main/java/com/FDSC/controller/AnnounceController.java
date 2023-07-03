package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/announce")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    @GetMapping("/slideShow")
    public Result slideShow() {
        return announceService.slideShow();
    }

    @GetMapping("/twoAnnounce")
    public Result twoAnnounce() { return announceService.twoAnnounce(); }

    @GetMapping("/announce")
    public Result announce(@RequestParam Integer id) { return announceService.getAnnounce(id); }
}
