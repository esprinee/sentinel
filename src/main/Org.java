package main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic Org class to store org information
 *
 */
public class Org {
    private String orgId;
    private String parentOrgId;
    private String orgName;
    private final List<User> orgUsers = new ArrayList<>();
    private final List<Org> childOrgs = new ArrayList<>();
    private int totalNumUsers;
    private int totalNumFiles;
    private int totalNumBytes;


    public Org(final String orgId, final String parentOrgId, final String orgName) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.parentOrgId = parentOrgId;
        this.totalNumUsers = 0;
        this.totalNumFiles = 0;
        this.totalNumBytes = 0;
    }

    public void setOrgId(final String orgId) {
        this.orgId = orgId;
    }
    public void setParentOrgId(final String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    public void addUser(final User user) {
        orgUsers.add(user);
    }
    public void addChildOrg(final Org org) {
        childOrgs.add(org);
    }
    public void removeOrg(final Org org) {
        childOrgs.remove(org);
    }
    public void removeUser(final User user){
        orgUsers.remove(user);
    }
    public List<Org> getChildOrgs() {
        List<Org> children = new ArrayList<>();
        for (Org org: childOrgs) {
            children.addAll(org.getChildOrgs());
        }
        children.addAll(childOrgs);
        return children;
    }

    public String getOrgId() {
        return orgId;
    }
    public String getParentOrgId() {
        return parentOrgId;
    }
    public String getOrgName() { return orgName; }
    public List<User> getOrgUsers() {
        return orgUsers;
    }

    /***
     * recursive call to get totalNumUsers from Child Orgs and from Users
     * @return
     */
    public Integer getTotalNumUsers() {
        totalNumUsers = 0;
        for (final Org org: childOrgs) {
            totalNumUsers += org.getTotalNumUsers();
        }
        totalNumUsers += orgUsers.size();
        return totalNumUsers;
    }

    /***
     * recursive call to get the totalNumberFiles from Child Orgs and from Users in the Org
     *
     * @return
     */
    public Integer getTotalNumFiles() {
        totalNumFiles =  0;
        for (final User user : orgUsers) {
            totalNumFiles += user.getNumFiles();
        }
        for (final Org org : childOrgs) {
            totalNumFiles += org.getTotalNumFiles();
        }
        return totalNumFiles;
    }

    /***
     * recursive call to get totalNumBytes from Child Orgs and from Users
     * @return
     */
    public Integer getTotalNumBytes() {
        totalNumBytes =  0;
        for (final Org org: childOrgs) {
            totalNumBytes += org.getTotalNumBytes();
        }
        for (final User user: orgUsers) {
            totalNumBytes += user.getNumBytes();
        }
        return totalNumBytes;
    }

    // calculate Number of users, files and bytes
    public void getOrgStat() {
        getTotalNumUsers();
        getTotalNumFiles();
        getTotalNumBytes();
    }

    /***
     * recursive call to get print out for each org
     * @param out
     * @param indent
     */
    public void getOrgStatus(final PrintWriter out, String indent) {
        out.println(indent + orgId + ", " + totalNumUsers + ", " + totalNumFiles+ ", " + totalNumBytes);
        indent = indent + "    ";
        for (final Org org: childOrgs) {
            org.getOrgStatus(out, indent);
        }
    }

}