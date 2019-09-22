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

import java.io.Serializable;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/5/19.
 */
public class SimpleJiraIssue implements Serializable {
    private String url;
    private String product; // Will be always JBEAP
    private String assignee;
    private String reporter;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getCombineSummaryDescriptionLabelsComponents() {
        return combineSummaryDescriptionLabelsComponents;
    }

    public void setCombineSummaryDescriptionLabelsComponents(String combineSummaryDescriptionLabelsComponents) {
        this.combineSummaryDescriptionLabelsComponents = combineSummaryDescriptionLabelsComponents;
    }

    public int getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(int issuePriority) {
        this.issuePriority = issuePriority;
    }

    public int getIssueType() {
        return issueType;
    }

    public void setIssueType(int issueType) {
        this.issueType = issueType;
    }

    private String combineSummaryDescriptionLabelsComponents; // combination of summary, description, labels, components
    private int issuePriority;
    private int issueType;

    public SimpleJiraIssue(String url,
                           String product,
                           String assignee,
                           String reporter,
                           String combineSummaryDescriptionLabelsComponents,
                           int issuePriority,
                           int issueType) {
        this.url = url;
        this.product = product;
        this.assignee = assignee;
        this.reporter = reporter;
        this.combineSummaryDescriptionLabelsComponents = combineSummaryDescriptionLabelsComponents;
        this.issuePriority = issuePriority;
        this.issueType = issueType;
    }
}
