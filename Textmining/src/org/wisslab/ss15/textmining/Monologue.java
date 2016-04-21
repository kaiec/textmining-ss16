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
public class Monologue {
    private Scene scene;
    private Speaker speaker;
    private String text;

    public Monologue(Scene scene, Speaker speaker, String text) {
        this.scene = scene;
        this.speaker = speaker;
        this.text = text;
        this.speaker.getMonologues().add(this);
        this.getScene().getAct().getWork().getMonologues().add(this);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    
    
    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Monologue{" + "speaker=" + speaker + ", scene=" + scene + ", text=" + text + "}\n";
    }
    
    

        
    
}
