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
