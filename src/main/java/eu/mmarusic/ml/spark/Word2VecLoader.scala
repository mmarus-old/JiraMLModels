/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2019, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package eu.mmarusic.ml.spark

import java.io.{DataInputStream, FileInputStream}
import java.util.zip.GZIPInputStream

import org.apache.spark.mllib.feature.Word2VecModel
/**
  * Created by Marek Marusic <mmarusic@redhat.com> on 9/23/19.
  */
object Word2VecLoader {
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
