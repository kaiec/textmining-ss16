/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kai
 */
public class Speaker {
   private String name;
   private Work work;
   private List<Monologue> monologues = new ArrayList<>();



    public List<Monologue> getMonologues() {
        return monologues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Speaker(String name, Work work) {
        this.name = name;
        this.work = work;
        this.work.getSpeakers().add(this);
    }

    public int getNumberOfMonologues() {
        return monologues.size();
    }

    public int getNumberOfWords() {
        int sum = 0;
        for (Monologue m: monologues) {
            sum += m.getText().split(" ").length;
        }
        return sum;
    }

    public double getTFIDF(String word) {
        Map<String, Double> tfmap = getTF();
        double tf = 0;
        if (tfmap.get(word)!=null) tf = tfmap.get(word);
        Map<String, Double> dfmap = getWork().getDF();
        double df = dfmap.get(word);
        int D = work.getSpeakers().size();
        return tf * Math.log(D/df);
    }

    public List<Double> getVSMVector() {
        List<Double> result = new ArrayList<>();
        for (String word: work.getDF().keySet()) {
            result.add(getTFIDF(word));
        }
        return result;
    }

    public Map<String, Double> getTF() {
        Map<String, Double> result = new HashMap<>();
        Pattern p1 = Pattern.compile("[a-zA-Z']+");

        for (String word: getAllText().split(" ")) {
            // Das ist quasi Tokenization, dafür gibt es Spezialbibliotheken
            word = word.replaceAll("\\,", "");
            word = word.replaceAll(".", "");
            word = word.replaceAll(":", "");
            word = word.replaceAll(";", "");
            word = word.toLowerCase();
            if (!p1.matcher(word).matches()) continue;
            result.putIfAbsent(word, 0d);
            result.put(word, result.get(word) + 1);
        }
        return result;
    }


    @Override
    public String toString() {
        return "Speaker{" + "name=" + name + ", work=" + work + '}';
    }


    public String getAllText() {
        StringBuilder res = new StringBuilder();
        for (Monologue m: monologues) {
            res.append(m.getText()).append(" ");
        }
        return res.toString();
    }





}
