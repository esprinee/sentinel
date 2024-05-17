import main.OrgCollection;
import main.Org;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by yingchuncai on 10/18/16.
 */
public class OrgCollectionTest {
    private final String testOrg1File  = "test/testOrg1.txt";
    private final String testUser1File = "test/testUser1.txt";

    private OrgCollection orgCollection = new OrgCollection();
    @Before
    public void setUp() {
        orgCollection.readOrgFile(testOrg1File);
        orgCollection.readUserFile(testUser1File);

    }

    @Test
    public void testReadOrgFile() {

        assert(orgCollection.getOrg("1") != null);
        Org org1 = orgCollection.getOrg("1");
        assert(org1.getChildOrgs().size() == 3);
        assert(org1.getOrgName().equals("Foo"));
        assert(org1.getParentOrgId().equals("null"));

        Org org2 = orgCollection.getOrg("5");
        assert(org2 != null);
        assert(org2.getChildOrgs().size() == 0 );
        assert(org2.getOrgId().equals("5"));

        Org org3 = orgCollection.getOrg("3");
        assert(org3 != null);
        assert(org3.getParentOrgId().equals("null"));

        assert(orgCollection.getOrg("11") == null);
    }

    @Test
    public void testReadUserFile() {

        Org org5 = orgCollection.getOrg("5");
        assert(org5.getTotalNumUsers() == 1);
        assert(org5.getTotalNumFiles() == 100);
        assert(org5.getTotalNumBytes() == 1000);
        Org org7 = orgCollection.getOrg("7");
        assert(org7.getTotalNumUsers() == 0);
        Org org2 = orgCollection.getOrg("2");
        assert(org2.getTotalNumUsers() == 2);
        assert(org2.getTotalNumFiles() == 133);
        assert(org2.getTotalNumBytes() == 1333);
        Org org1 = orgCollection.getOrg("1");
        assert(org1.getTotalNumUsers() == 5);
        orgCollection.printStat();
    }

    @Test
    public void testGetOrgTree() {
        List<Org> orgTree = orgCollection.getOrgTree("1", true);
        assert(orgTree.size() == 4);
        orgTree = orgCollection.getOrgTree("1", false);
        assert(orgTree.size() == 3);
        orgTree = orgCollection.getOrgTree("2", true);
        assert(orgTree.size() == 2);
        orgTree = orgCollection.getOrgTree("7", false);
        assert(orgTree.size() == 0);
        orgTree = orgCollection.getOrgTree("11", true);
        assert(orgTree.size() == 0);

    }

}