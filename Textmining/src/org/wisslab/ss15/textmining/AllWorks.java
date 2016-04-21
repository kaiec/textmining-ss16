/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static javafx.scene.input.KeyCode.H;

/**
 *
 * @author kai
 */
public class AllWorks {
    private List<Work> works = new ArrayList<>();
    
    public void add(Work work) {
        works.add(work);
    }
    
    public void addAll(AllWorks aw) {
        works.addAll(aw.getAll());
    }
    
    public List<Work> getAll() {
        return works;
    }
    
    public List<Speaker> getAllSpeakers() {
        List<Speaker> res = new ArrayList<>();
        for (Work w: works) {
            res.addAll(w.getSpeakers());
        }
        Collections.sort(res, (speaker1, speaker2) -> speaker2.getNumberOfMonologues()-speaker1.getNumberOfMonologues());
        return res;
    }
    
    public List<String> getUniqueWords() {
        Set<String> words = new HashSet<>();
        for (Work w: works) {
            words.addAll(w.getDF().keySet());
        }
        return new ArrayList<String>(words);
    }
    
    private Map<String, Double> dfMap = null;
    public Map<String, Double> getDF() {
        if (dfMap!=null) return dfMap;
        Map<String, Double> res = new HashMap<>();
        for (Speaker s:getAllSpeakers()) {
            for (String word: s.getTF().keySet()) {
                res.putIfAbsent(word, 0d);
                res.put(word, res.get(word) + 1);
            
            }
        }
        dfMap = res;
        System.out.println("Global Document frequencies calculates for " + getAllSpeakers().size() + " speakers. Number of words: " + dfMap.keySet().size());
        return res;
    }
    
    public List<Double> getDFList(List<String> words) {
        List<Double> res = new ArrayList<>();
        for (String w: words) {
            res.add(getDF().getOrDefault(w, 0d));
        }
        return res;
    }

}
