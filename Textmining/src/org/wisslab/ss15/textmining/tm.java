/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.List;

/**
 *
 * @author kai
 */
public class tm {
    
    public static void main(String[] args) {
        ShakespeareParser sp = new ShakespeareParser();
        AllWorks works = null;
        if (args.length==0) {
            works = sp.readFiles("C:\\Users\\kai\\Downloads\\ShakespearePlaysPlus\\TXT");
        } else {
            works = sp.readFiles(args[0]);
        }
        List<String> words = works.getUniqueWords();
        List<Double> dfs = works.getDFList(words);
        System.out.println("Unique words: " + words.size());
        for (int i = words.size() - 1; i >= 0; i--) {
            if (dfs.get(i)>50) {
                words.remove(i);
                dfs.remove(i);
            }
        }
        System.out.println("Unique words after 50 cut: " + words.size());
        for (int i = words.size() - 1; i >= 0; i--) {
            if (dfs.get(i)>20) {
                words.remove(i);
                dfs.remove(i);
            }
        }
        System.out.println("Unique words after 20 cut: " + words.size());
        for (int i = words.size() - 1; i >= 0; i--) {
            if (dfs.get(i)>10) {
                words.remove(i);
                dfs.remove(i);
            }
        }
        System.out.println("Unique words after 10 cut: " + words.size());
        
        System.out.println(words);
        
        for (Speaker speaker: works.getAllSpeakers()) {
                System.out.print(speaker + " ----> ");
                List<Double> s1vec = speaker.getVSMVector(words, true);
                double maxSim = 0;
                Speaker maxSpeaker = null;
                for (Speaker s2: works.getAllSpeakers()) {
                    // Kein Vergleich mit sich selbst!
                    if (speaker.equals(s2)) continue;
                    
                    // Ähnlichkeitsberechnung, vgl. https://en.wikipedia.org/wiki/Cosine_similarity
                    List<Double> s2vec = s2.getVSMVector(words, true);
                    double sim = 0;
                    double n1 = 0, n2 = 0;
                    for (int i = 0; i < s1vec.size(); i++) {
                        sim += s1vec.get(i) * s2vec.get(i);
                        n1 += Math.pow(s1vec.get(i), 2);
                        n2 += Math.pow(s2vec.get(i), 2);
                    }
                    n1 = Math.sqrt(n1);
                    n2 = Math.sqrt(n2);
                    sim = sim / (n1*n2);
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
