package com.galvanize.Checkpoint;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/encode")
    public String encode(
            @RequestParam(value="message") String message,
            @RequestParam(value="key") String key
    ) {
        String result = "";
        boolean match = false;
        String[] alphabetArr = "abcdefghijklmnopqrstuvwxyz".split("");
        String[] keyArr = key.split("");

        for (char x : message.toCharArray()) {
            Character ch = x;
            for (int i = 0; i < alphabetArr.length; i++) {
                if (ch.toString().equals(alphabetArr[i])) {
                    result += keyArr[i];
                    match = true;
                    break;
                }
            }
            if (!match) {
                result += ch;
            }
            match = false;
        }
        return result;
    }

    @PostMapping("/s/{find}/{replace}")
    public String sed(
            @PathVariable Map<String, String> querystring,
            @RequestBody String text
            ) {
        String result = "";
        String find =querystring.get("find");
        String replace =querystring.get("replace");

        String[] textArr = text.split(" ");

        for (int i = 0; i < textArr.length ; i++) {
            String word = textArr[i];
            if (word.equals(find)) {
                    word = replace;
            }
            if (i == textArr.length - 1) {
                result += word;
            } else {
                result += word + " ";
            }
        }

        return result;
    }
}
