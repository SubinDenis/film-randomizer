package by.personal.filmrandomizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;

@Controller
@RequestMapping("/integration")
public class IntegrationController {

    @PostMapping("/send-file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        try(Scanner sc = new Scanner(file.getInputStream())) {
            while (sc.hasNext()) {
                System.out.println(sc.next());
            }
        }

        return "redirect:/get-film";

    }

}
