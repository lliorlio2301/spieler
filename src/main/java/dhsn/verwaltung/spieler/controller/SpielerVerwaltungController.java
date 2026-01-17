package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/spielerverwaltung")
public class SpielerVerwaltungController {
    @GetMapping("/startseite")
    public String getMethodName() {
        return "startseite";
    }

}
