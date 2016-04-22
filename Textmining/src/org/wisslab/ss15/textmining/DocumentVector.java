/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kai
 */
public class DocumentVector {

    private VectorSpace vectorSpace;
    private String id;

    public DocumentVector(VectorSpace vectorSpace, String id) {
        this.vectorSpace = vectorSpace;
        this.id = id;
    }

    private Map<String, Long> tfMap = new HashMap<>();

    public void addToken(String token) {
        long old = tfMap.getOrDefault(token, 0l);
        tfMap.put(token, old + 1);
    }

    public Set<String> getDimensions() {
        return Collections.unmodifiableSet(tfMap.keySet());
    }

    public long getTf(String dimension) {
        return tfMap.getOrDefault(dimension, 0l);
    }

    public double getTfIdf(String dimension) {
        if (getTf(dimension) == 0) {
            return 0;
        }
        return getTf(dimension) * Math.log(((double) vectorSpace.getNumberOfDocuments()) / (vectorSpace.getDF(dimension)));
    }

    private double norm = -1;

    public double getTfIdfNormSqr() {
        if (norm > -0.5) {
            return norm;
        }
        norm = 0;
        for (String k : getDimensions()) {
            norm += getTfIdf(k) * getTfIdf(k);
        }
        return norm;
    }
}
