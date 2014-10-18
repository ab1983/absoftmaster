/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.util;

import java.text.Normalizer;

/**
 *
 * @author Agnaldo
 */
public class AlgoUtil {
    public static String normalizerName(String name){
        return Normalizer
                    .normalize(name, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-zA-Z0-9]+", "").toLowerCase();
    }
}
