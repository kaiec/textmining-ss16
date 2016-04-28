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

    // Tokenizer tokenizer = null;

    public NLPWrapper() {
        /*
        InputStream modelIn = null;
        try {
            modelIn = new FileInputStream("en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        */

    }

    // Unser Behelfs-Tokenizer aus der Vorlesung
    public List<String> tokenize(String input) {
        List<String> result = new ArrayList<>();
        /* result.addAll(Arrays.asList(tokenizer.tokenize(input)));
         return result;
         */
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
