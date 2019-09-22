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
