package be.unamur.hermes.dataaccess.entity;

public class Document {

    private long documentID;
    private long requestID;
    private String contents;
    private long documentTitleID;


    public Document(long documentID, long requestID, long documentTitleID) {
        this.documentID = documentID;
        this.requestID = requestID;
        this.documentTitleID = documentTitleID;
    }

    public Document(long documentID, long requestID, String contents, long documentTitleID) {
        this.documentID = documentID;
        this.requestID = requestID;
        this.contents = contents;
        this.documentTitleID = documentTitleID;
    }

    public long getDocumentID() {
        return documentID;
    }

    public long getRequestID() {
        return requestID;
    }

    public String getContents() {
        return contents;
    }

    public long getDocumentTitleID() {
        return documentTitleID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDocumentTitleID(long documentTitleID) {
        this.documentTitleID = documentTitleID;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentID=" + documentID +
                ", requestID=" + requestID +
                ", contents='" + contents + '\'' +
                ", documentTitleID=" + documentTitleID +
                '}';
    }
}
