package com.example.getlocalhelp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.getlocalhelp.service.*;

@Controller
public class LocalHelpController {

    // GetNearbyMatchService nbMatch = new GetNearbyMatchService();

    public final GetNearbyMatchService nbMatch;

    public LocalHelpController(GetNearbyMatchService nbMatch) {
        this.nbMatch = nbMatch;
    }

    @GetMapping("/localhelp")
    public String helpPage(Model model) {
        return "test";
    }

    @GetMapping("/create_request")
    public String createRequest(Model model) {
        return "create_request";
    }

    @GetMapping("/view_requests")
    public String viewRequests() {

        return "view_request";
    }

    @PostMapping("/display_requests")
    public String displayRequests(@RequestParam String loc_code, @RequestParam String contact, Model model) {
        List<Map<String, Object>> resultset = nbMatch.searchResult(loc_code, contact);
        model.addAttribute("requests", resultset);
        model.addAttribute("loc_code", loc_code);
        model.addAttribute("contact", contact);
        return "view_request";
    }

    @PostMapping("/save_request")
    public String saveRequest(@RequestParam String loc_code, @RequestParam String msg, @RequestParam String contact,
            @RequestParam String duration, Model model) {
        String status = nbMatch.saveRequestToDB(loc_code, msg, contact, duration);
        model.addAttribute("status_text", status);
        return "create_request";
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
