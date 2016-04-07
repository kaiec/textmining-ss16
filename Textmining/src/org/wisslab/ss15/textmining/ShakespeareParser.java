/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
        List<String> path = new ArrayList<String>();
        StringBuffer tmp = new StringBuffer();
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
                    path.add(tagname);
                    //System.out.println("You are here: " + path.toString());

                    // Vielleicht etwas paranoid, aber haben wir wirklich das passende End-Tag?    
                } else if (path.size() > 0 && tagname.equals(path.get(path.size() - 1))) {
                    // System.out.println("End: " + tagname);
                    Monologue mon = new Monologue();
                    mon.setText(tmp.toString().trim());
                    mon.setSpeaker(tagname);
                    mon.setPath(new ArrayList<String>(path));
                    tmp.setLength(0);
                    path.remove(path.size() - 1);
                    if (mon.getText().length()>0) {
                        work.add(mon);
                    }
                    // System.out.println("You are here: " + path.toString());
                }
            } else {
                // Wenn es kein Tag ist, schauen wir noch, ob die Zeile eingerückt ist, falls ja: Text
                if (line.startsWith("\t")) {
                    tmp.append(line.replaceFirst("\t", "")).append(" ");
                }
            }

        }

        return work;

    }
}
