package io.github.vdaburon.jmeter.utils;

import org.apache.commons.csv.CSVRecord;
import java.util.Comparator;

public class CompareRecord implements Comparator<CSVRecord> {
    public int compare(CSVRecord a, CSVRecord b) {
        // sort by the first column : Label
        return a.get(0).compareTo(b.get(0));
    }
}
