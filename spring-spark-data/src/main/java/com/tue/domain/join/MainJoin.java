package com.tue.domain.join;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

//http://www.techburps.com/misc/apache-spark-dataset-joins-in-java/129

public class MainJoin {
    public static void main(String[] args) {
        Dataset<Row> fruitData = DataProvider.getFruitData();
        fruitData.show();

        Dataset<Row> dataFamilyData = DataProvider.getDataFamily();
        dataFamilyData.show();

        /* INNER JOIN IN Spark Java */
        Dataset<Row> innerJoinData = fruitData.join(dataFamilyData,
                fruitData.col("dataFamilyId").equalTo(dataFamilyData.col("dataFamilyId")));
        innerJoinData.show();

        /* OUTER JOIN Spark Java */
//        Dataset<Row> outerJoinData = fruitData.join(dataFamilyData,
//                fruitData.col("dataFamilyId").equalTo(dataFamilyData.col("dataFamilyId")), "outer");
//        outerJoinData.show();


        /* CROSS JOIN Spark Java */
//        Dataset<Row> leftantiJoinData = fruitData.join(dataFamilyData,
//                fruitData.col("dataFamilyId").equalTo(dataFamilyData.col("dataFamilyId")), "leftanti");
//        leftantiJoinData.show();
    }
}
