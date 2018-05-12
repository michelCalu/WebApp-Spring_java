package be.unamur.hermes.dataaccess.entity;

public class Document {

    private long documentID;
    private long requestID;
    private String contents;
    private String documentTitle;

    public Document() {
    }

    public Document(long requestID, long documentID, String documentTitle) {
        this.documentID = documentID;
        this.requestID = requestID;
        this.documentTitle = documentTitle;
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

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentID=" + documentID +
                ", requestID=" + requestID +
                ", contents='" + contents + '\'' +
                ", documentTitle='" + documentTitle + '\'' +
                '}';
    }
}
