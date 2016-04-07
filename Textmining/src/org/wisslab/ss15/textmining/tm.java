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
        Work work = null;
        if (args.length==0) {
            work = sp.readFile("C:\\Users\\kai\\Downloads\\ShakespearePlaysPlus\\TXT\\historical\\The Tragedy of King Richard II.txt");
        } else {
            work = sp.readFile(args[0]);
        }
        
        for (String name: work.getSpeakers()) {
            System.out.println(name + ": " + work.getNumberOfMonologuesBySpeaker(name) + " times, " + work.getWordsBySpeaker(name) + " words, " + work.getWordsBySpeaker(name)/work.getNumberOfMonologuesBySpeaker(name) + " words per monologue.");
        }
        
        
    }
    
}
