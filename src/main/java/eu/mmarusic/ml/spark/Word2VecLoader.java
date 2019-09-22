
package eu.mmarusic.ml.spark;

import org.apache.spark.mllib.feature.Word2VecModel;

import java.io.DataInputStream;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/22/19.
 */
public class Word2VecLoader {
    public Word2VecModel loadBin(String file) {

    }

    private String readUntil(DataInputStream inputStream, char term, int maxLength =  1024 * 8) {
        var char: Char = inputStream.readByte().toChar
        val str = new StringBuilder
        while (!char.equals(term)) {
            str.append(char)
            assert(str.size < maxLength)
            char = inputStream.readByte().toChar
    }

    def loadBin(file: String) = {
        def readUntil(inputStream: DataInputStream, term: Char, maxLength: Int = 1024 * 8): String = {
                var char: Char = inputStream.readByte().toChar
        val str = new StringBuilder
        while (!char.equals(term)) {
            str.append(char)
            assert(str.size < maxLength)
            char = inputStream.readByte().toChar
        }
        str.toString
  }
        val inputStream: DataInputStream = new DataInputStream(new GZIPInputStream(new FileInputStream(file)))
        try {
            val header = readUntil(inputStream, '\n')
            val (records, dimensions) = header.split(" ") match {
                case Array(records, dimensions) => (records.toInt, dimensions.toInt)
            }
            new Word2VecModel((0 until records).toArray.map(recordIndex => {
                    readUntil(inputStream, ' ') -> (0 until dimensions).map(dimensionIndex => {
                    java.lang.Float.intBitsToFloat(java.lang.Integer.reverseBytes(inputStream.readInt()))
            }).toArray
    }).toMap)
        } finally {
            inputStream.close()
        }
    }
}
