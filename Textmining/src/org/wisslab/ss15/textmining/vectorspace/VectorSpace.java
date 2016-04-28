/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining.vectorspace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kai
 */
public class VectorSpace {

    private Map<String, DocumentVector> index = new HashMap<>();
    private Map<String, Long> dfMap = new HashMap<>();

    public long getNumberOfDocuments() {
        return index.keySet().size();
    }

    public long getDF(String dimension) {
        return dfMap.getOrDefault(dimension, 0l);
    }
    
    public DocumentVector getDocument(String id) {
        return index.get(id);
    }

    public DocumentPair getNearestNeighbour(DocumentVector dv) {
        double maxSim = 0;
        DocumentVector maxDocument = null;
        for (String id : index.keySet()) {
            if (dv.getId().equals(id)) {
                continue;
            }
            DocumentVector dv2 = index.get(id);
            double sim = this.getCosineSimilarity(dv.getId(), id);
            if (sim > maxSim) {
                maxSim = sim;
                maxDocument = dv2;
            }
        }
        return new DocumentPair(dv, maxDocument, maxSim);
     
    }
        // Ähnlichkeitsberechnung, vgl. https://en.wikipedia.org/wiki/Cosine_similarity
        // http://stackoverflow.com/questions/3622112/vector-space-model-algorithm-in-java-to-get-the-similarity-score-between-two-peo
    public double getCosineSimilarity(String id1, String id2) {
        DocumentVector v1 = index.get(id1);
        if (v1 == null) {
            throw new RuntimeException("No document for id: " + id1);
        }
        DocumentVector v2 = index.get(id2);
        if (v2 == null) {
            throw new RuntimeException("No document for id: " + id2);
        }
        Set<String> both = new HashSet(v1.getDimensions());
        both.retainAll(v2.getDimensions());
        double scalar = 0;
        for (String k : both) {
            scalar += v1.getTfIdf(k) * v2.getTfIdf(k);
        }
        return scalar / (v1.getTfIdfNorm() * v2.getTfIdfNorm());
    }

    public void addDocument(String id, List<String> tokens) {
        if (index.get(id) != null) {
            throw new RuntimeException("Document ID already exists: " + id);
        }
        DocumentVector dv = new DocumentVector(this, id);
        Set<String> newTokens = new HashSet<>();
        for (String t : tokens) {
            dv.addToken(t);
            newTokens.add(t);
        }
        for (String t : newTokens) {
            long old = dfMap.getOrDefault(t, 0l);
            dfMap.put(t, old + 1);
        }
        index.put(id, dv);
    }


}
