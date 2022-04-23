package btp400.assignment1.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    Admin admin;

    public void setupAdmin() {
        admin.setFile("resources/admin/admins.json");
        admin.readDataFromFile(true);
    }

    public void setupLib(){
        admin.setFile("resources/admin/librariansUnitTestData.json");
        admin.readDataFromFile(false);
    }

    @BeforeEach
    public void newAdmin(){
        admin = new Admin();
    }

    @DisplayName("Tests for Admin Login")
    @Nested
    class adminLoginTest {
        @Test
        @DisplayName("Test Admin Login Success")
        void testAdminLoginSuccess(){
            setupAdmin();
            assertEquals("Success", admin.login("pskills","spicyp43"));
        }

        @Test
        @DisplayName("Test Admin Login Fail")
        void testAdminLoginFail(){
            setupAdmin();
            assertEquals("The username or password entered does not exist in the system. Please try again.",
                    admin.login("notValidUsername", "notValidPassword"));
        }
    }

    @DisplayName("Tests for Register Librarian")
    @Nested
    class RegisterLibrarianTest {
        @Test
        @DisplayName("Test Fname Error")
        void testFnameError(){
            setupLib();
            assertEquals("Only alphabetical symbols are allowed for first name",
                    admin.registerLibrarian("123", "qwop", "e@e.com", "pw"));
        }

        @Test
        @DisplayName("Test Lname Error")
        void testLnameError(){
            setupLib();
            assertEquals("Only alphabetical symbols are allowed for last name",
                    admin.registerLibrarian("first", "123", "d@d.com", "pw"));
        }

        @Test
        @DisplayName("Test Email Error")
        void testEmailError(){
            setupLib();
            assertEquals("Email Address must follow the following format: 'example@email.com'",
                    admin.registerLibrarian("first","last","asdf","pw"));
        }

        @Test
        @DisplayName("Test Email Exists")
        void testEmailExists(){
            setupLib();
            assertEquals("Email already exists in the system, please enter a different email",
                    admin.registerLibrarian("first","last","nn@raptors.ca","pw"));
        }

        @Test
        @DisplayName("Test Register Success")
        void testRegisterSuccess(){
            setupLib();
            assertEquals("Success",
                    admin.registerLibrarian("tester","tester","tester@tester.com","pw"));
        }
    }

    @DisplayName("Tests for Delete Librarian")
    @Nested
    class DeleteLibrarianTest {
        @Test
        @DisplayName("Test Delete Fail")
        void testDeleteFail(){
            setupLib();
            assertEquals("No Librarian matches the employee Id entered.",
                    admin.deleteLibrarian(999));
        }

        @Test
        @DisplayName("Test Delete Success")
        void testDeleteSuccess(){
            setupLib();
            assertEquals("Success",
                    admin.deleteLibrarian(100));
        }
    }

    @DisplayName("Tests for View Librarians")
    @Nested
    class ViewLibrariansTest {
        @Test
        @DisplayName("Test View Librarians Success")
        void testViewLibrariansSuccess(){
            setupLib();
            JSONArray testArr = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("id",100);
            obj.put("name", "Nick Nurse");
            obj.put("email", "nn@raptors.ca");
            obj.put("password", "boxn1");
            testArr.add(obj);
            assertEquals(testArr.toString(), admin.viewLibrarians().toString());
        }
    }
}
