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
public class Scene {
    
    private int number;
    private Act act;

    public Scene(Act act, int number) {
        this.number = number;
        this.act = act;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }
    
    
}
