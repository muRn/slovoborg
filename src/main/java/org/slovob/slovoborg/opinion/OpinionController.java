package org.slovob.slovoborg.opinion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public void processOpinion(@RequestBody Opinion opinion, HttpServletRequest request) {
        opinion.setIpAddress(request.getRemoteAddr());
        service.processOpinion(opinion);
    }
}
