package eu.mmarusic.ml.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/23/19.
 */
public class SparkSetup {
    private SparkConf conf;
    private JavaSparkContext sc;
    private SparkSession spark;


    public void startSparkLocaly() {
        conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("DataVec Example");
        sc = new JavaSparkContext(conf);
    }

    public void startWithSessionName(String name) {
        startSparkLocaly();
        spark = SparkSession
                .builder()
                .appName(name)
                .getOrCreate();
    }


    public SparkSession getSpark() {
        return spark;
    }

    public void setSpark(SparkSession spark) {
        this.spark = spark;
    }
}
