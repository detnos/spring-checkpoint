package com.galvanize.Checkpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextServicePagesController {

    @GetMapping("/camelize")
    public String camelize(
            @RequestParam(value="original") String original,
            @RequestParam(value="initialCap", required = false, defaultValue = "false") boolean initialCap
    ) {
        String result = "";
        String[] originalArr = original.split("_");

        for (int i = 0; i < originalArr.length ; i++) {
            String word = originalArr[i];
            String firstLetter = word.substring(0, 1);
            String rest = word.substring(1);

            if (!initialCap && i == 0) {
                //do nothing
            } else {
                firstLetter = firstLetter.toUpperCase();
            }
            result += firstLetter + rest;
        }

        return result;
    }
}
