// --== CS400 File Header Information ==--
// Name: Weijie Zhou
// Email: wzhou226@wisc.edu
// Team: CG
// Role: Test Engineer 1
// TA: Yeping Wang
// Lecturer: Gary Daul
// Notes to Grader: <optional extra notes>
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogInAndRegister {

    /**
     * This method tests addUser method.
    */
    @Test
    public void testAddUser() {
        LogInAndRegister test = new LogInAndRegister();
        test.addUser("username", "password", true);
        if(!test.isUserExist("username"))
            fail("add user method fails");
    }

    /**
     * This method tests isUserExist method
    */
    @Test
    public void testIsUserExist(){
        LogInAndRegister test = new LogInAndRegister();
        test.addUser("username", "password", true);
        if (!test.isUserExist("username"))
            fail("isUserExist method fails");

    }

    /**
     * This method tests logIn method
    */
    @Test
    public void testLogIn() {
        LogInAndRegister test = new LogInAndRegister();
        test.addUser("username", "password", true);
        if(!test.logIn("username", "password"))
            fail("logIn method fails");
    }

    /**
     * This method tests isAdmin method
    */
    @Test
    public void testIsAdmin(){
        LogInAndRegister test = new LogInAndRegister();
        test.addUser("username", "password", true);
        if(!test.isAdmin("username"))
            fail("isAdmin method fails ");
    }

}
