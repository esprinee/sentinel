package main;

/**
 * User class to store user information: userId, orgId, number of files and number of bytes
 */
public class User {
    private String userId;
    private String orgId;
    private int numFiles;
    private int numBytes;

    public User() {}
    public User(final String userId, final String orgId, final int numFiles, final int numBytes) {
        this.userId = userId;
        this.orgId = orgId;
        this.numFiles = numFiles;
        this.numBytes = numBytes;
    }
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    public void setOrgId(final String orgId) {
        this.orgId = orgId;
    }
    public void setNumFiles(final int numFiles) {
        this.numFiles = numFiles;
    }
    public void setNumBytes(final int numBytes) {
        this.numBytes = numBytes;
    }

    public String getOrgId() {
        return orgId;
    }
    public int getNumFiles() {
        return numFiles;
    }
    public int getNumBytes() {
        return numBytes;
    }

    public String getUserId() {
        return userId;
    }

}