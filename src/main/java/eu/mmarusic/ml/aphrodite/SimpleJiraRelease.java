package eu.mmarusic.ml.aphrodite;

import java.io.Serializable;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/5/19.
 */
public class SimpleJiraRelease implements Serializable {
    private int candidateReleases;
    private int version;
    private SimpleJiraIssue[] issues;

    public SimpleJiraRelease(int candidateReleases, int version, SimpleJiraIssue[] issues) {
        this.candidateReleases = candidateReleases;
        this.version = version;
        this.issues = issues;
    }

    public int getCandidateReleases() {
        return candidateReleases;
    }

    public int getVersion() {
        return version;
    }

    public SimpleJiraIssue[] getIssues() {
        return issues;
    }

}

