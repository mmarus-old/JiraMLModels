package eu.mmarusic.ml.aphrodite;

import org.jboss.set.aphrodite.domain.Issue;
import org.jboss.set.aphrodite.domain.User;
import org.jboss.set.aphrodite.issue.trackers.jira.JiraIssue;
import org.jboss.set.aphrodite.issue.trackers.jira.JiraLabel;
import org.jboss.set.aphrodite.issue.trackers.jira.JiraRelease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/5/19.
 */
public class ReleaseDataSetCreator {

    public static List<SimpleJiraRelease> convertReleases(Collection<JiraRelease> jiraReleases) {
        List<SimpleJiraRelease> releases = new ArrayList<>(jiraReleases.size());
        for (JiraRelease jiraRelease : jiraReleases) {
            List<SimpleJiraIssue> issues = new ArrayList<>(jiraRelease.getIssues().size());

            for(Issue issue:  jiraRelease.getIssues()) {
                if (issue instanceof JiraIssue) {
                    issues.add(createIssueFromJiraIssue((JiraIssue) issue));
                } else {
                    //Log errors
                }

            }

            releases.add(new SimpleJiraRelease(jiraRelease.getCandidateReleases().size(),
                    //TODO: check if the long to int is not problematic
                    jiraRelease.getVersion().getId().intValue(),
                    issues.toArray(new SimpleJiraIssue[0])));

        }
        return releases;
    }

    private static SimpleJiraIssue createIssueFromJiraIssue(JiraIssue issue) {
        // combination of summary, description, labels, components
        StringBuilder sb = new StringBuilder().append(issue.getSummary().orElse(""))
                .append(" ")
                .append(issue.getDescription().orElse(""))
                .append(" ")
                .append(issue.getLabels().stream().map(JiraLabel::getName).collect(Collectors.joining( "," )))
                .append(" ")
                .append(issue.getComponents().stream().collect(Collectors.joining(",")));


        return new SimpleJiraIssue(issue.getURL().toString(),
                issue.getProduct().orElse(""),
                issue.getAssignee().orElse(new User("", "")).getEmail().orElse(""),
                issue.getReporter().orElse(new User("", "")).getEmail().orElse(""),
                sb.toString(),
                issue.getPriority().getPriorityScore(),
                issue.getType().ordinal()
                );

    }

}
