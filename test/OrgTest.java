import org.junit.Assert;
import org.junit.Test;
import main.Org;
import main.User;
import java.util.List;
/**
 * Created by yingchuncai on 10/18/16.
 */
public class OrgTest {
    @Test
    public void constructorTest() {
        Org newOrg = new Org("1", "null","testOrg");
        assert(newOrg.getOrgId().equals("1"));
        assert(newOrg.getParentOrgId().equals("null"));
        assert(newOrg.getOrgName().equals("testOrg"));
        assert(newOrg.getTotalNumUsers() == 0);
        assert(newOrg.getTotalNumFiles() == 0);
        assert(newOrg.getTotalNumBytes() ==  0);
    }

    private Org setupOrg() {
        Org newOrg = new Org("1", "null", "Foo");
        newOrg.addChildOrg(new Org("2", "1", "Bar"));
        newOrg.addChildOrg(new Org("5", "1", "Qux"));

        newOrg.addUser(new User("1", "1", 10, 200));
        newOrg.addUser(new User("2", "1", 20, 400));
        return newOrg;
    }

    private Org addMoreUser(Org org) {
        List<Org> childOrgs = org.getChildOrgs();
        childOrgs.get(0).addUser(new User("3", "2", 50, 2000));
        childOrgs.get(0).addUser(new User("4", "2", 50, 2000));
        childOrgs.get(1).addUser(new User("5", "5", 100, 1000));
        return org;
    }
    @Test
    public void testGetTotalNumUsers() {
        Org newOrg = setupOrg();
        assert(newOrg.getTotalNumUsers() == 2);
        List<Org> childOrgs = newOrg.getChildOrgs();
        assert(childOrgs.size()== 2);
        newOrg = addMoreUser(newOrg);
        assert(newOrg.getTotalNumUsers()== 5);
    }

    @Test
    public void testGetTotalNumFiles() {
        Org newOrg = setupOrg();
        assert(newOrg.getTotalNumFiles() == 30);
        newOrg = addMoreUser(newOrg);
        assert(newOrg.getTotalNumFiles() == 230);
    }

    @Test
    public void testGetTotalNumBytes() {
        Org newOrg = setupOrg();
        assert(newOrg.getTotalNumBytes() == 600);
        newOrg = addMoreUser(newOrg);
        assert(newOrg.getTotalNumBytes() == 5600);
    }
}