package com.galvanize.Checkpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/redact")
    public String redact(
            @RequestParam(value="original") String original,
            @RequestParam(value="badWord") List<String> badWords
    ) {
        String result = "";
        String[] originalArr = original.split(" ");

        for (int i = 0; i < originalArr.length ; i++) {
            String word = originalArr[i];
            for (int j = 0; j < badWords.size(); j++) {
                System.out.println("word: " + word + " badWord: " + badWords.get(j));
                if (word.equals(badWords.get(j))) {

                    word = word.replaceAll("(?i)[a-z]", "*");
                }
            }
            if (i == originalArr.length - 1) {
                result += word;
            } else {
                result += word + " ";
            }
        }

        return result;
    }
}
