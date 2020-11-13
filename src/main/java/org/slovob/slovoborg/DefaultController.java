package org.slovob.slovoborg;

import org.slovob.slovoborg.definition.DefinitionDto;
import org.slovob.slovoborg.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showMainPage(Model model, @AuthenticationPrincipal User user) {
        if (user != null) {
            model.addAttribute("user", user.getName());
            model.addAttribute("role", user.getRole());
        }

        List<DefinitionDto> definitions = service.getDefinitions(user);
        model.addAttribute(definitions);
        return "index";
    }
}
