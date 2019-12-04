package com.com2008.journalmanagementsystem.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import com.com2008.journalmanagementsystem.util.database.Database;
import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class SubmissionDocument implements IDataRow {
    private String issn;
    private String submissionID;
    private InputStream firstDraft;
    private InputStream finalDraft;

    public SubmissionDocument() {

    }

    // public SubmissionDocument(String issn, String submissionID, InputStream firstDraft, InputStream finalDraft) {
    //     this.issn = issn;
    //     this.submissionID = submissionID;
    //     this.firstDraft = firstDraft;
    //     this.finalDraft = finalDraft;
    // }

    public SubmissionDocument(String issn, String submissionID, File firstDraft, File finalDraft) throws FileNotFoundException {
        this.issn = issn;
        this.submissionID = submissionID;
        if(firstDraft != null)
            this.firstDraft = new FileInputStream(firstDraft);
        if(finalDraft != null)
            this.finalDraft = new FileInputStream(finalDraft);
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String submissionID) {
        this.submissionID = submissionID;
    }

    public InputStream getFirstDraft() {
        return firstDraft;
    }

    public void downloadFirstDraft(File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = firstDraft.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
    }

    public void setFirstDraft(InputStream firstDraft) {
        this.firstDraft = firstDraft;
    }

    public void setFirstDraft(String firstDraftPath) throws FileNotFoundException {
        this.firstDraft = new FileInputStream(firstDraftPath);
    }

    public InputStream getFinalDraft() {
        return finalDraft;
    }

    public void downloadFinalDraft(File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = finalDraft.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
    }

    public void setFinalDraft(InputStream finalDraft) {
        this.finalDraft = finalDraft;
    }

    public void setFinalDraft(String finalDraftPath) throws FileNotFoundException {
        this.finalDraft = new FileInputStream(finalDraftPath);
    }

    public static void main(String[] args) throws SQLException, IOException {
        Database.connect("jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");

        String documentFolder = "/Users/boxuanshan/Documents/GitHub/COM2008Project/";
        String filename = "dummy.pdf";

        // Database.write("SubmissionDocument", new SubmissionDocument("ISSN 0000-0000", "2971e39f-4530-46a8-836b-dfd2754042ec", new File(documentFolder + filename), null));
        // Database.update("SubmissionDocument", new SubmissionDocument("ISSN 0000-0000", "2971e39f-4530-46a8-836b-dfd2754042ec", null, null), new SubmissionDocument(null, null, null, new File(documentFolder + filename)), false);
        
        List<SubmissionDocument> sds = Database.read("SubmissionDocument", new SubmissionDocument("ISSN 0000-0000", "2971e39f-4530-46a8-836b-dfd2754042ec", null, null));
        SubmissionDocument sd = sds.get(0);
        sd.downloadFirstDraft(new File(documentFolder + "first.pdf"));
        sd.downloadFinalDraft(new File(documentFolder + "final.pdf"));

        Database.disconnect();
    }

}