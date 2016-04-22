/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author kai
 */
public class NLPWrapper {

    // Unser Behelfs-Tokenizer aus der Vorlesung
    public List<String> tokenize(String input) {
        List<String> result = new ArrayList<>();
        Pattern p1 = Pattern.compile("[a-zA-Z']+");
        for (String word : input.split(" ")) {
            // Das ist quasi Tokenization, dafür gibt es Spezialbibliotheken
            word = word.replaceAll(",", "");
            word = word.replaceAll("\\.", "");
            word = word.replaceAll(":", "");
            word = word.replaceAll(";", "");
            word = word.toLowerCase();
            if (!p1.matcher(word).matches()) {
                continue;
            }
            result.add(word);
        }
        return result;
    }

}
