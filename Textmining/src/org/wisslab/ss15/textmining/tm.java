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
        List<Work> works = null;
        if (args.length==0) {
            works = sp.readFiles("C:\\Users\\kai\\Downloads\\ShakespearePlaysPlus\\TXT");
        } else {
            works = sp.readFiles(args[0]);
        }
        for (Work work: works) {
            for (Speaker speaker: work.getSpeakers()) {
                System.out.println(speaker.getName() + ": " + work.getNumberOfMonologuesBySpeaker(speaker) + " times, " + work.getWordsBySpeaker(speaker) + " words, " + work.getWordsBySpeaker(speaker)/work.getNumberOfMonologuesBySpeaker(speaker) + " words per monologue.");
            }
        }
        
        
    }
    
}
