/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import org.wisslab.ss15.textmining.vectorspace.VectorSpace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
