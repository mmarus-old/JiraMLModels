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
