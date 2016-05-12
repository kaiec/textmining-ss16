/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 *
 * @author kai
 */
public class NLPWrapper {

    Tokenizer tokenizer = null;
    SentenceDetector sentenceDetector = null;
    NameFinderME personFinder = null;
    NameFinderME locationFinder = null;

    public NLPWrapper() {
        InputStream modelIn = null;
        try {
            modelIn = new FileInputStream("en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
            modelIn.close();
            modelIn = new FileInputStream("en-sent.bin");
            SentenceModel sentModel = new SentenceModel(modelIn);
            sentenceDetector = new SentenceDetectorME(sentModel);
            modelIn.close();
            modelIn = new FileInputStream("en-ner-location.bin");
            TokenNameFinderModel locationModel = new TokenNameFinderModel(modelIn);
            locationFinder = new NameFinderME(locationModel);
            modelIn.close();
            modelIn = new FileInputStream("en-ner-person.bin");
            TokenNameFinderModel personModel = new TokenNameFinderModel(modelIn);
            personFinder = new NameFinderME(personModel);
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
    
    }

    public List<String> splitSentences(String input) {
        return Arrays.asList(sentenceDetector.sentDetect(input));
    }

    public List<String> findPersons(List<String> tokens) {
        Span[] spans = personFinder.find((String[]) tokens.toArray());
        List<String> result = new ArrayList<>();
        for (Span s: spans) {
            StringBuilder name = new StringBuilder();
            for (int i = s.getStart();i<s.getEnd();i++) {
                if (i>s.getStart()) {
                    name.append(" ");
                }
                name.append(tokens.get(i));
            }
            result.add(name.toString());
        }
        return result;
    }
    

    public List<String> findLocations(List<String> tokens) {
        Span[] spans = locationFinder.find( (String[]) tokens.toArray() );
        List<String> result = new ArrayList<>();
        for (Span s: spans) {
            StringBuilder name = new StringBuilder();
            for (int i = s.getStart();i<s.getEnd();i++) {
                if (i>s.getStart()) {
                    name.append(" ");
                }
                name.append(tokens.get(i));
            }
            result.add(name.toString());
        }
        return result;
    }
    

    
    // Unser Behelfs-Tokenizer aus der Vorlesung
    public List<String> tokenize(String input) {
        return Arrays.asList(tokenizer.tokenize(input));
        
        /*
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
        */
    }

}
