package by.haardd.cclog.controller.mvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestsController {


    @GetMapping
    public String requestsPage(Model model) {
        return "requests";
    }


}
