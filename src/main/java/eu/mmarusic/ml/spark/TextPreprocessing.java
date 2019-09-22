package eu.mmarusic.ml.spark;


import eu.mmarusic.ml.aphrodite.AphroditeConnection;
import eu.mmarusic.ml.aphrodite.ReleaseSerializer;
import eu.mmarusic.ml.aphrodite.SimpleJiraIssue;
import eu.mmarusic.ml.aphrodite.SimpleJiraRelease;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/4/19.
 *
 */
public class TextPreprocessing {

    public static void main(String[] args) throws Exception {
        processData();
    }

    public static void processData() {
        List<SimpleJiraIssue> data = new ArrayList<>();

        Collection<SimpleJiraRelease> releases = ReleaseSerializer.deserialize(AphroditeConnection.fileName);
        data.addAll(Arrays.asList(releases.iterator().next().getIssues()));

//        data.add(new SimpleJiraIssue("", "", "", "", "", 1, 1));

        //We'll use Spark local to handle our data

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("DataVec Example");

        JavaSparkContext sc = new JavaSparkContext(conf);

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaIndexToStringExample")
                .getOrCreate();

        StructType schema = new StructType(new StructField[]{
                new StructField("url", DataTypes.StringType, false, Metadata.empty()),
                new StructField("product", DataTypes.StringType, false, Metadata.empty()),
                new StructField("assignee", DataTypes.StringType, false, Metadata.empty()),
                new StructField("reporter", DataTypes.StringType, false, Metadata.empty()),
                new StructField("combineSummaryDescriptionLabelsComponents", DataTypes.StringType, false, Metadata.empty()),
                new StructField("issuePriority", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("issueType", DataTypes.IntegerType, false, Metadata.empty())
        });
        Dataset<Row> df = spark.createDataFrame(data, SimpleJiraIssue.class);


        //Data Processing


        StringIndexerModel assigneeIndexer = new StringIndexer()
                .setInputCol("assignee")
                .setOutputCol("assigneeIndex")
                .fit(df);
        StringIndexerModel reporterIndexer = new StringIndexer()
                .setInputCol("reporter")
                .setOutputCol("reporterIndex")
                .fit(df);
        //Word2vec



        Dataset<Row> indexed = assigneeIndexer.transform(df);
        indexed = reporterIndexer.transform(indexed);

        System.out.println("Transformed string column '" + assigneeIndexer.getInputCol() + "' " +
                "to indexed column '" + assigneeIndexer.getOutputCol() + "'");
        df.show();
        indexed.show();

//        StructField inputColSchema = indexed.schema().apply(indexer.getOutputCol());
//        System.out.println("StringIndexer will store labels in output column metadata: " +
//                Attribute.fromStructField(inputColSchema).toString() + "\n");
//
//        IndexToString converter = new IndexToString()
//                .setInputCol("labelsIndex")
//                .setOutputCol("originalLabels");
//        Dataset<Row> converted = converter.transform(indexed);
//
//        System.out.println("Transformed indexed column '" + converter.getInputCol() + "' back to " +
//                "original string column '" + converter.getOutputCol() + "' using labels in metadata");
//        converted.select("labels", "labelsIndex", "originalLabels").show();

        

        // $example off$
        spark.stop();
    }

    //TODO: Try TF-IDF vectors
    //TODO: With SVM models

}
