package org.slovob.slovoborg.opinion;

import org.slovob.slovoborg.exception.FishyFrontendQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/opinion")
public class OpinionController {
    private OpinionService service;

    @Autowired
    public OpinionController(OpinionService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void processOpinion(@RequestBody OpinionTransfer opinion, HttpServletRequest request) {
        if (opinion.getOpinion() != 1 && opinion.getOpinion() != -1) {
            throw new FishyFrontendQuery("There is no button with opinion " + opinion.getOpinion());
        }

        service.processOpinion(opinion, request.getRemoteHost());
    }
}
