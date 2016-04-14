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
public class Speaker {
   private String name;
   private Work work;

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
    }
    
    public int getNumberOfMonologues() {
        return work.getNumberOfMonologuesBySpeaker(this);
    }

    public int getNumberOfWords() {
        return work.getWordsBySpeaker(this);
    }

    
    @Override
    public String toString() {
        return "Speaker{" + "name=" + name + ", work=" + work + '}';
    }
    
    
   
   
    
    
    
}
