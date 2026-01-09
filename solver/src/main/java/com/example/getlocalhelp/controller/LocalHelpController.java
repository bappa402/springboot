package com.example.getlocalhelp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.getlocalhelp.service.*;

@Controller
public class LocalHelpController {

    //GetNearbyMatchService nbMatch = new GetNearbyMatchService();

    public final GetNearbyMatchService nbMatch;

    public LocalHelpController(GetNearbyMatchService nbMatch) {
        this.nbMatch = nbMatch;
    }

    @GetMapping("/localhelp")
    public String helpPage(Model model) {
        return "test";
    }

    @RequestMapping("/showNeighbours")
    public String showNeighbours(@RequestParam String loc_code, @RequestParam String range, Model model) {
        String output = nbMatch.test(loc_code, range);
        String dbOutput = nbMatch.getDataFromDB(5);

        model.addAttribute("key", output);
        model.addAttribute("range", range);
        model.addAttribute("loc_code", loc_code);
        model.addAttribute("dbOutput", dbOutput);
        return "test";
    }
}
