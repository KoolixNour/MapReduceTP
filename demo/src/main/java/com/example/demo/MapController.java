package com.example.demo;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class MapController {
    int nb;
    @Autowired mapService service;
    @PostMapping("/init")
    public String init(){
        service.init();
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("count",nb);
    return"index";
    }
    @PostMapping("/choose")
    public String submit(@RequestParam @NotNull MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        System.out.println(file.getName());
        service.submit(file);
        return "redirect:/index";
    }
    @PostMapping("/count")
    public String submit(@RequestParam String nom, Model model){
        int count = Integer.parseInt(service.nbOcc(nom));
nb=count;
        return "redirect:/index";

    }

}
