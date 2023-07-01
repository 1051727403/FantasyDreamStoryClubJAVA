package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/allTags")
    public Result allTags() {
        return searchService.allTags();
    }

    @GetMapping("/search")
    public Result search(@RequestParam Long tag,
                         @RequestParam String sort,
                         @RequestParam Integer page) {
        return searchService.search(tag, sort, page);
    }

}
