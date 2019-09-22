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

package eu.mmarusic.ml.aphrodite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/21/19.
 */
public class ReleaseSerializer {

    public static void serialize(Collection<SimpleJiraRelease> releases, String outputName) {
        // Let's serialize an Object
        try {
            FileOutputStream fileOut = new FileOutputStream(outputName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(releases);
            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<SimpleJiraRelease> deserialize(String inputName) {
        Collection<SimpleJiraRelease> readReleases = null;
        // Let's deserialize an Object
        try {
            FileInputStream fileIn = new FileInputStream(inputName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            readReleases = (Collection<SimpleJiraRelease>) in.readObject();
            System.out.println("Deserialized Data: \n" + readReleases.toString());
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return readReleases;
    }

}
