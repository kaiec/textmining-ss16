/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kai
 */
public class Work {
    private List<Monologue> monologues = new ArrayList<>();
    private List<Speaker> speakers = new ArrayList<>();
    private String filename;
    
    public Work() {
        
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
    
    

    public void sortSpeakersByMonologues() {
        Collections.sort(speakers, 
                (speaker1, speaker2) -> speaker1.getNumberOfMonologues() - speaker2.getNumberOfMonologues()
        );
    }
    
    public List<Speaker> getSpeakers() {
        return speakers;
    }
    
    public Speaker getOrCreateSpeaker(String name) {
        for (Speaker s: speakers) {
            if (s.getName().toLowerCase().equals(name.toLowerCase())) {
                return s;
            }
        }
        return new Speaker(name, this);
    }
    
    public List<Monologue> getMonologues() {
        return monologues;
    }
    
    
    
    
    public String getAllText() {
        StringBuilder res = new StringBuilder();
        for (Monologue m: monologues) {
            res.append(m.getText()).append(" ");
        }
        return res.toString();
    }

}
