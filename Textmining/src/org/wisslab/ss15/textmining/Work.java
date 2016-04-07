/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kai
 */
public class Work {
    private List<Monologue> monologues = new ArrayList<>();
    private Map<String, List<Monologue>> bySpeaker = new HashMap<>();
    
    public Work() {
    }
    
    public List<String> getSpeakers() {
        List<String> res = new ArrayList<>();
        for (String name: bySpeaker.keySet()) {
            res.add(name);
        }
        Collections.sort(res, new Comparator<String>(){
            @Override
            public int compare(String name1, String name2) {
                return bySpeaker.get(name2).size() - bySpeaker.get(name1).size();
            }
        });
        return res;
    }
    
    public void add(Monologue m) {
        monologues.add(m);
        if (bySpeaker.get(m.getSpeaker())==null) {
            bySpeaker.put(m.getSpeaker(), new ArrayList<Monologue>());
        } 
        bySpeaker.get(m.getSpeaker()).add(m);
    }
    
    public int getWordsBySpeaker(String name) {
        int sum = 0;
        for (Monologue m: bySpeaker.get(name)) {
            sum += m.getText().split(" ").length;
        }
        return sum;
    }
    
    public int getNumberOfMonologuesBySpeaker(String name) {
        return bySpeaker.get(name).size();
    }

}
