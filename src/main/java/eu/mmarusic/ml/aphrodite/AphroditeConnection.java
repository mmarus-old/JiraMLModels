package eu.mmarusic.ml.aphrodite;


import org.jboss.set.aphrodite.Aphrodite;
import org.jboss.set.aphrodite.issue.trackers.jira.JiraIssueTracker;
import org.jboss.set.aphrodite.issue.trackers.jira.JiraRelease;
import org.jboss.set.aphrodite.simplecontainer.SimpleContainer;

import java.util.Collection;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/4/19.
 */
public class AphroditeConnection {
    public static final String fileName = "releases.txt";


    public static void main(String[] args) throws Exception {
        downloadData();
    }


    private static void downloadData() throws Exception {
        System.setProperty("cacheDir", "/home/mmarusic/Devel/aphrodite/cache");
        System.setProperty("aphrodite.config", "/home/mmarusic/Devel/aphrodite/aphrodite.properties.json");
        System.setProperty("cacheName", "github-cache");
        System.setProperty("cacheSize", "10");

        try (Aphrodite aphrodite = Aphrodite.instance()) {
            SimpleContainer container = (SimpleContainer) SimpleContainer.instance();
            JiraIssueTracker issueTrackerService = container.lookup(JiraIssueTracker.class.getSimpleName(), JiraIssueTracker.class);

            //TODO: Get only 1 release
            Collection<JiraRelease> releases = JiraRelease.findAll();

            Collection<SimpleJiraRelease> simpleReleases = ReleaseDataSetCreator.convertReleases(releases);
            ReleaseSerializer.serialize(simpleReleases, fileName);
            Collection<SimpleJiraRelease> releasesRead = ReleaseSerializer.deserialize(fileName);
            System.out.println(releasesRead);
        }
    }


}
