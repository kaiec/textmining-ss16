/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
public class ShakespeareParser {

    public Work readFile(String filename) {

        // Neue Strategie: Erst mal den gesamten Text in einen String einlesen, damit wir
        // zeilenübergreifend bereinigen können.
        String fulltext = "";
        Work work = new Work();
        work.setFilename(filename);
        Map<String, Speaker> speakers = new HashMap<>();

        // Google: java read text file line by line
        // 1. Treffer: http://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
        try {
            StringBuilder ftBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_16LE));
            String line;
            while ((line = br.readLine()) != null) {
                ftBuilder.append(line).append("\n");
            }
            fulltext = ftBuilder.toString();
        } catch (IOException ioe) {
            throw new RuntimeException("Could not read file: " + filename, ioe);
        }

        // STAGE DIR Bereinigung
        // Löschen und durch Tab (\t) ersetzen, damit der folgende Text normal verarbeitet wird.
        Pattern p1 = Pattern.compile("<STAGE DIR>[\\n\\w\\W\\s]*?</STAGE DIR>");
        Matcher m1 = p1.matcher(fulltext);
        fulltext = m1.replaceAll("\t");

        // Ab hier wieder alte Strategie, zeilenweise Verarbeitung
        StringBuffer tmp = new StringBuffer();
        Act act = null;
        Scene scene = null;
        for (String line : fulltext.split("\n")) {
            // Google: java regular expression
            // 2. Treffer: http://www.tutorialspoint.com/java/java_regular_expressions.htm
            // Create a Pattern object
            // Das soll alle Tags erwischen, die keine Kleinbuchstaben enthalten
            // Erste Gruppe: Ein / oder nix, je nachdem ob es Start- oder Endtag ist.
            // Zweite Gruppe: Der Tagname
            Pattern r = Pattern.compile("^<(/?)([^/>a-z]+)>");
            // Now create matcher object.
            Matcher m = r.matcher(line);

            if (m.find()) {
                // Haben wir ein Starttag?
                boolean starttag = !m.group(1).equals("/");
                // Und wie heißt es?
                String tagname = m.group(2);
                if (starttag) {
                    // System.out.println("Start: " + tagname);
                    if (tagname.startsWith("ACT ")) {
                        // String ist "ACT 1" --> "ACT " löschen
                        act = new Act(work, Integer.parseInt(tagname.replace("ACT ", "")));
                        // actNr = Integer.parseInt(tagname.substring(tagname.indexOf(" ") + 1));
                        // actNr = Integer.parseInt(tagname.substring("ACT ".length()));
                        // actNr = Integer.parseInt(tagname.substring(4));
                        // actNr = Integer.parseInt(tagname.substring(4));
                        // actNr = Integer.parseInt(tagname.split(" ")[1]);
                    }
                    if (tagname.startsWith("SCENE ")) {
                        if (act==null) throw new RuntimeException("ACT FEHLT: " + work.getFilename() + " / " + tagname);
                        scene = new Scene(act, Integer.parseInt(tagname.replace("SCENE ", "")));
                    }

                    //System.out.println("You are here: " + path.toString());

                    // Endtag
                } else {
                    // System.out.println("End: " + tagname);

                    String text = tmp.toString().trim();
                    if (text.length()==0) {
                        continue;
                    }

                    Speaker speaker = work.getOrCreateSpeaker(tagname);
                    Monologue mon = new Monologue(scene, speaker, text);
                    tmp.setLength(0);
                }
            } else {
                // Wenn es kein Tag ist, schauen wir noch, ob die Zeile eingerückt ist, falls ja: Text
                if (line.startsWith("\t")) {
                    tmp.append(line.trim()).append(" ");
                }
            }

        }

        return work;

    }
    
    public AllWorks readFiles(String directory) {
        AllWorks res = new AllWorks();
        File dir = new File(directory);
        for (File f: dir.listFiles()) {
            if (f.isFile() && f.getName().toLowerCase().endsWith(".txt")) {
                res.add(readFile(f.getAbsolutePath()));
            } else if (f.isDirectory() && !f.getName().toLowerCase().endsWith("_characters")) {
                res.addAll(readFiles(f.getAbsolutePath()));
            }
        }
        return res;
    }
}
