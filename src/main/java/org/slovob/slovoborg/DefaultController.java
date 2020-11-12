package org.slovob.slovoborg;

import org.slovob.slovoborg.definition.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class DefaultController {
    private DefaultService service;

    @Autowired
    public DefaultController(DefaultService service) {
        this.service = service;
    }

    @GetMapping
    public String showMainPage(Model model, HttpServletRequest request, Principal principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }

        List<Definition> definitions = service.getDefinitions(request.getRemoteAddr());
        model.addAttribute(definitions);
        return "index";
    }
}
