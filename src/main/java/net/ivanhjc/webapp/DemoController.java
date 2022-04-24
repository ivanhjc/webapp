package net.ivanhjc.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/")
public class DemoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("greeting")
    public String greeting(@RequestParam(name="name", defaultValue = "world") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("helloworld")
    @ResponseBody
    public String helloWorld() {
        String msg = "Hello World!";
        log.info(msg);
        log.debug(msg);
        log.trace(msg);
        log.warn(msg);
        log.error(msg);
        return msg;
    }
}