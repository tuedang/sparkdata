package com.tue.domain.join;

import com.tue.config.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Arrays;
import java.util.List;

public final class DataProvider {
    private static List<Data> dataList = Arrays.asList(
            new Data(1, "AA", "Apple", 1),
            new Data(2, "AB", "Orange", 1),
            new Data(3, "AC", "Banana", 2),
            new Data(4, "AD", "Guava", 3));

    private static List<DataFamily> dataFamilyList = Arrays.asList(
            new DataFamily(1, "Pu Family", "USA"),
            new DataFamily(2, "Ou Family", "USA"),
            new DataFamily(4, "Lu Family", "France"));

    public static Dataset<Row> getFruitData() {
        return SparkContext.sqlContext().createDataFrame(dataList, Data.class);
    }

    public static Dataset<Row> getDataFamily() {
        return SparkContext.sqlContext().createDataFrame(dataFamilyList, DataFamily.class);
    }
}
