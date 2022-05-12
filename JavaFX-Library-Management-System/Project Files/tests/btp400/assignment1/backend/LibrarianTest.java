package btp400.assignment1.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianTest {
    Admin admin;

    public void setupLibLogin(){
        admin.setFile("resources/librarian/librariansUnitTestData.json");
        admin.readDataFromFile(false);
    }

    public void setupLibDB(int stock){
        Librarian.setPath("resources/librarian/libraryUnitTestData.json");
        Librarian.readFile();

        JSONArray testArr = new JSONArray();
        JSONObject testBook = new JSONObject();
        JSONArray waitingList = new JSONArray();
        waitingList.add(123456);
        JSONArray borrowedBy = new JSONArray();
        borrowedBy.add(987654);
        borrowedBy.add(654321);

        testBook.put("pages", 1);
        testBook.put("waitingList", waitingList);
        testBook.put("author", "William");
        testBook.put("borrowedBy", borrowedBy);
        testBook.put("name", "DemoBook");
        testBook.put("ID", 123456);
        testBook.put("stock", stock);

        testArr.add(testBook);
        Librarian.libraryRecord.put("book", testArr);
        Librarian.writeFile();
    }

    @BeforeEach
    public void newAdmin(){
        admin = new Admin();
    }

    @DisplayName("Tests for Librarian Login")
    @Nested
    class LibrarianLoginTest {
        @Test
        @DisplayName("Test Librarian Login Success")
        void testLibrarianLoginSuccess(){
            setupLibLogin();
            assertEquals("Success", admin.libLogin(100,"100"));
        }

        @Test
        @DisplayName("Test Librarian Login Fail")
        void testLibrarianLoginFail(){
            setupLibLogin();
            assertEquals("The id or password entered does not exist in the system. Please try again.",
                    admin.libLogin(0, "notValidPassword"));
        }
    }

    @DisplayName("Tests for Getting/Viewing All Books")
    @Nested
    class GetAllBooksTest {
        @Test
        @DisplayName("Test Get/View All Books Success")
        void testGetAllBooksSuccess(){
            setupLibDB(0);

            JSONArray testArr = new JSONArray();
            JSONObject testBook = new JSONObject();
            JSONArray waitingList = new JSONArray();
            waitingList.add(123456);
            JSONArray borrowedBy = new JSONArray();
            borrowedBy.add(987654);
            borrowedBy.add(654321);

            testBook.put("pages", 1);
            testBook.put("waitingList", waitingList);
            testBook.put("author", "William");
            testBook.put("borrowedBy", borrowedBy);
            testBook.put("name", "DemoBook");
            testBook.put("ID", 123456);
            testBook.put("stock", 0);

            testArr.add(testBook);
            assertEquals(testArr.toString(), Librarian.getBooks().toString());
        }
    }

    @DisplayName("Tests for Getting/Viewing A Book")
    @Nested
    class GetBookTest {
        @Test
        @DisplayName("Test Get/View Book Success")
        void testGetBookSuccess(){
            setupLibDB(0);
            JSONObject testBook = new JSONObject();
            JSONArray waitingList = new JSONArray();
            waitingList.add(123456);
            JSONArray borrowedBy = new JSONArray();
            borrowedBy.add(987654);
            borrowedBy.add(654321);

            testBook.put("pages", 1);
            testBook.put("waitingList", waitingList);
            testBook.put("author", "William");
            testBook.put("borrowedBy", borrowedBy);
            testBook.put("name", "DemoBook");
            testBook.put("ID", 123456);
            testBook.put("stock", 0);

            assertEquals(testBook.toString(), Librarian.getBook(123456).toString());
        }

        @Test
        @DisplayName("Test Get Book Unsuccessful")
        void testGetBookUnsuccessful(){
            setupLibDB(0);
            assertNull(Librarian.getBook(10101010));
        }
    }


    @DisplayName("Tests for Adding A Book")
    @Nested
    class AddBookLibrarianTest {
        @Test
        @DisplayName("Test Successful Book Add.")
        void testSuccessBookAdd(){
            setupLibDB(0);
            assertEquals("Success",
                    Librarian.addBook("12121212", "UnitTest", "Shakespeare", "12", "11"));
        }

        @Test
        @DisplayName("Test Unsuccessful Book Add.")
        void testUnsuccessfulBookAdd(){
            setupLibDB(0);
            assertEquals("A Book is already present in library with same ISBN!",
                    Librarian.addBook("123456", "UnitTest", "Shakespeare", "12", "11"));
        }

        @Test
        @DisplayName("Validate ID Book Add.")
        void validateIDBookAdd(){
            setupLibDB(0);
            assertEquals("Only numbers are allowed for ISBN field!",
                    Librarian.addBook("abc", "UnitTest", "Shakespeare", "12", "11"));
        }

        @Test
        @DisplayName("Validate Name Book Add.")
        void validateNameBookAdd(){
            setupLibDB(0);
            assertEquals("Only alphabets are allowed for Book Name field!",
                    Librarian.addBook("123456", "123", "Shakespeare", "12", "11"));
        }

        @Test
        @DisplayName("Validate Author Book Add.")
        void validateAuthorBookAdd(){
            setupLibDB(0);
            assertEquals("Only alphabets are allowed for Author Name field!",
                    Librarian.addBook("123456", "UnitTest", "123", "12", "11"));
        }

        @Test
        @DisplayName("Validate Pages Book Add.")
        void validatePagesBookAdd(){
            setupLibDB(0);
            assertEquals("Only numbers are allowed for Total Pages field!",
                    Librarian.addBook("123456", "UnitTest", "Shakespeare", "abc", "11"));
        }

        @Test
        @DisplayName("Validate Stock Book Add.")
        void validateStockBookAdd(){
            setupLibDB(0);
            assertEquals("Only numbers are allowed for Total Stock field!",
                    Librarian.addBook("123456", "UnitTest", "Shakespeare", "12", "abc"));
        }
    }

    @DisplayName("Tests for Returning A Book")
    @Nested
    class ReturnBookLibrarianTest {
        @Test
        @DisplayName("Test Successful Book Return.")
        void testSuccessBookReturn(){
            setupLibDB(0);
            assertTrue(Librarian.returnBook(987654, 123456));
        }

        @Test
        @DisplayName("Test Unsuccessful Book Return.")
        void testUnsuccessfulBookReturn(){
            setupLibDB(0);
            assertFalse(Librarian.returnBook(99999, 123456));
        }
    }

    @DisplayName("Tests for Borrowing/WaitingList A Book")
    @Nested
    class BorrowBookLibrarianTest {
        @Test
        @DisplayName("Test Successful Book Borrow.")
        void testSuccessBookBorrow(){
            setupLibDB(1);
            assertTrue(Librarian.borrowBook(10101010, 123456));
        }

        @Test
        @DisplayName("Test Unsuccessful/WaitingList Book Borrow.")
        void testUnsuccessfulBookBorrow(){
            setupLibDB(0);
            assertFalse(Librarian.borrowBook(91919191, 123456));
        }
    }

    @DisplayName("Tests for Deleting A Book")
    @Nested
    class DeleteBookTest {
        @Test
        @DisplayName("Test Delete Success")
        void testDeleteSuccess(){
            setupLibDB(0);
            assertTrue(Librarian.deleteBook(123456));
        }
    }
}
