/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

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
        
        if (args.length==0) {
            works = sp.readFiles("C:\\Users\\kai\\Downloads\\ShakespearePlaysPlus\\TXT");
        } else {
            works = sp.readFiles(args[0]);
        }

        // Vector Space erzeugen
        for (Speaker speaker: works.getAllSpeakers()) {
            vs.addDocument(speaker.toString(), nlp.tokenize(speaker.getAllText()));
        }
        
        for (Speaker speaker: works.getAllSpeakers()) {
                System.out.print(speaker + " ----> ");
                double maxSim = 0;
                Speaker maxSpeaker = null;
                for (Speaker s2: works.getAllSpeakers()) {
                    // Kein Vergleich mit sich selbst!
                    if (speaker.equals(s2)) continue;
                    double sim = vs.getCosineSimilarity(speaker.toString(), s2.toString());
                    if (sim>maxSim) {
                        maxSim = sim;
                        maxSpeaker = s2;
                    }
                }
                System.out.println("Most similar: " + maxSpeaker + " ("+maxSim+")");
                System.out.println("-------------------------------");
            }
        
    }

    
    
    
}
