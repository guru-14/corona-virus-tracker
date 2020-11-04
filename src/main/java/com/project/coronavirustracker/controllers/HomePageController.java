package com.project.coronavirustracker.controllers;

import com.project.coronavirustracker.models.CoronaVirusStats;
import com.project.coronavirustracker.services.CoronaVirusDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    CoronaVirusDataFetcher coronaVirusDataFetcher;

    @GetMapping("/")
    public String homePage(Model model){
        List<CoronaVirusStats> stats = coronaVirusDataFetcher.getStats();
        String totalReportedCases = String.format("%,d", stats.stream().mapToInt(stat -> stat.getNumberOfCases()).sum());
        String totalNewCases = String.format("%,d", stats.stream().mapToInt(stat -> stat.getNumberOfNewCases()).sum());
        model.addAttribute("stats", stats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}