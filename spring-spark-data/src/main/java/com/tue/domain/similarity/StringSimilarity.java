package com.tue.domain.similarity;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.util.Locale;

public class StringSimilarity {
    private static final FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());
    private static final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    public static double similarScore(String s1, String s2) {
        return 1.0D - jaroWinklerDistance.apply(s1, s2);
    }

    public static boolean isSimilar(String s1, String s2, int score) {
        return similarScore(s1, s2) > score;
    }

    public static boolean isSimilar(String s1, String s2) {
        int score = fuzzyScore.fuzzyScore(s1, s2);
        return score > 50 && score > (s1.length() + s2.length()) / 2;
    }

    public static double isSimilarAddress(String s1, String s2) {
        if (StringUtils.isAnyEmpty(s1, s2)) {
            return 0;
        }
        return (double) FuzzySearch.ratio(s1, s2) / 100;
    }
}
