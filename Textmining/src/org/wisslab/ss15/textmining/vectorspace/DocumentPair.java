/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisslab.ss15.textmining.vectorspace;

/**
 *
 * @author kai
 */
public class DocumentPair implements Comparable<DocumentPair> {

        public DocumentVector dv1, dv2;
        public double similarity;

        public DocumentPair(DocumentVector dv1, DocumentVector dv2, double similarity) {
            this.dv1 = dv1;
            this.dv2 = dv2;
            this.similarity = similarity;
        }

        @Override
        public int compareTo(DocumentPair t) {
            return Double.compare(this.similarity, t.similarity);

        }

    }
