/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.ArrayList;
import java.util.List;

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
