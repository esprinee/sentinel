package main;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

/**
 * class to read in files for org and user
 *
 */
public class OrgCollection{
    private final List<Org> orgCollections = new ArrayList<Org>();

    /***
     * get the Org based on orgId. If not found, return null
     * @param orgId
     * @return
     */
    public Org getOrg(final String orgId) {
        Org result = null;
        if (orgId != null) {
            result = orgCollections.stream()
                    .filter(org -> org.getOrgId().equals(orgId))
                    .findAny()
                    .orElse(null);
        }
        return result;
    }

    /**
     * get OrgTree
     * @param orgId
     * @param inclusive
     * @return
     */
    public List<Org> getOrgTree(final String orgId, final boolean inclusive) {
        final List<Org> orgTree = new ArrayList<Org>();
        if (orgId != null) {
            final Org rootOrg = getOrg(orgId);
            if (rootOrg != null) {
                orgTree.addAll(rootOrg.getChildOrgs());
                if (inclusive) {
                    orgTree.add(rootOrg);
                }
            }
        }
        return orgTree;
    }

    /***
     * print out the status of the org tree to a file
     */
    public void printStat() {

        // from root org, calculate the org stat
        orgCollections.stream().filter(org -> org.getParentOrgId().equals("null"))
                .forEach(org->org.getOrgStat());

        try(FileWriter fw = new FileWriter("test/output");
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
        {
            final String indent = "";
            //write out to a file for each org's status
            orgCollections.stream().filter(org -> org.getParentOrgId().equals("null"))
            .forEach(org->org.getOrgStatus(out, indent));

            bw.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public void readOrgFile(final String fileName) {
        try {
            Files.lines(Paths.get(fileName))
                    .map(line -> line.split(",", 3)) // read each line, split line
                    .map(array -> new Org(array[0].trim(), array[1].trim(), array[2].trim())) // create new Org
                    .forEach(orgCollections::add);// add to the org list

            // create a orgId to org map for easy find
            final Map<String, Org> orgMap = orgCollections.stream().collect(Collectors.toMap(org->org.getOrgId(), org->org));
            // build org tree
            orgCollections.stream().filter(org -> !org.getParentOrgId().equals("null")).forEach(org -> {
                Org parentOrg = orgMap.get(org.getParentOrgId());
                if (parentOrg == null) {
                    System.out.println("wrong org with no parent Org, or no null as Parent");
                } else {
                    orgMap.get(org.getParentOrgId()).addChildOrg(org);
                }
            });

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public void readUserFile(final String userFile) {
        try {
            final Map<String, Org> orgMap = orgCollections.stream().collect(Collectors.toMap(org->org.getOrgId(), org->org));

            Files.lines(Paths.get(userFile))
                    .map(line -> line.split(",", 4)) // readline, split line
                    .map(array -> new User(array[0].trim(), array[1].trim(), // create user
                            Integer.parseInt(array[2].trim()), Integer.parseInt(array[3].trim())))
                    .forEach(user -> orgMap.get(user.getOrgId()).addUser(user));// add user to org


        } catch (final IOException e) {
            e.printStackTrace();
        }
    }


}