
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
public class Act {
    
    private Work work;
    private int number;

    public Act(Work work, int number) {
        this.work = work;
        this.number = number;
    }
    
    

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
