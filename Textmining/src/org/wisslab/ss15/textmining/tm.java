/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import org.wisslab.ss15.textmining.vectorspace.VectorSpace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.wisslab.ss15.textmining.vectorspace.DocumentPair;

/**
 *
 * @author kai
 */
public class tm {

    public static void main(String[] args) {
        ShakespeareParser sp = new ShakespeareParser();
        AllWorks works = null;
        NLPWrapper nlp = new NLPWrapper();
        VectorSpace vs = new VectorSpace();

        if (args.length == 0) {
            works = sp.readFiles("C:\\Users\\kai\\Downloads\\ShakespearePlaysPlus\\TXT");
        } else {
            works = sp.readFiles(args[0]);
        }

        // Kleine Rollen rauswerfen...
        int minLength = 0;

        for (Speaker speaker : works.getAllSpeakers()) {
            Set<String> persons = new HashSet<>();
            Set<String> locations = new HashSet<>();
            List<String> sentences = nlp.splitSentences(speaker.getAllText());
            for (String sentence: sentences) {
                List<String> tokens = nlp.tokenize(sentence);
                persons.addAll(nlp.findPersons(tokens));
                locations.addAll(nlp.findLocations(tokens));
            }
            System.out.println(speaker.toString() + " talks about persons: ");
            for (String name: persons) {
                System.out.println("   " + name);
            }
            System.out.println();
            System.out.println(speaker.toString() + " talks about locations: ");
            for (String name: locations) {
                System.out.println("   " + name);
            }
            System.out.println();

        }
        

        // Vector Space erzeugen
        for (Speaker speaker : works.getAllSpeakers()) {
            if (speaker.getAllText().length() < minLength) {
                continue;
            }
            vs.addDocument(speaker.toString(), nlp.tokenize(speaker.getAllText()));
        }

        
        
        System.out.println("Calculating pairs:");

        List<DocumentPair> pairs = new ArrayList<>();
        for (Speaker speaker : works.getAllSpeakers()) {
            if (speaker.getAllText().length() < minLength) {
                continue;
            }
            pairs.add(vs.getNearestNeighbour(vs.getDocument(speaker.toString())));
            System.out.print(".");
        }

        System.out.println();
        System.out.println("Done.");

        Collections.sort(pairs);
        for (DocumentPair p : pairs) {
            System.out.println(p.dv1.getId() + " ---> " + p.dv2.getId() + " (" + p.similarity + ")");
        }

    }

}
