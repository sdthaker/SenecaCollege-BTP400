package btp400.assignment1.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    Student student;

    @BeforeEach
    public void setUpStudent() {
        student = new Student();
        student.setFile("resources/student/studentUnitTestData.json");
        student.readDataFromFile();
        Librarian.setPath("resources/student/studentLibrarianUnitTestData.json");
    }

    @Test
    @DisplayName("Get name of the student")
    void get_Name(){
        student.setSid("122432434");
        assertEquals("Molly Heave",student.getName());

        student.setSid("123456783");
        assertEquals("",student.getName());
    }

    @DisplayName("Grouped tests for Student Login")
    @Nested
    class LoginTest {
        @Test
        @DisplayName("Student ID exists in the file")
        void check_If_SID_Exists() {
            assertEquals("Success",
                    student.login("123456789"));
        }

        @Test
        @DisplayName("Student ID does not exist in the file")
        void check_If_SID_Not_Exists() {
            assertEquals("This user doesn't exist in the system. " +
                    "Please try different number!",
                    student.login("123456783"));
        }

        @Test
        @DisplayName("Student ID consists of only numbers")
        void check_If_SID_Is_Only_Numbers() {
            assertEquals("Only numbers are allowed for SID field!",
                    student.login("1a3456783"));
        }

        @Test
        @DisplayName("Student ID is exactly 9 digits in length")
        void check_If_SID_Is_9_Digits_In_Length() {
            assertEquals("SID must be exactly 9 digits in length!",
                    student.login("13456783"));
        }
    }

    @DisplayName("Grouped tests for Student Registration")
    @Nested
    class RegisterTest {

        @Test
        @DisplayName("Student ID consists of only numbers")
        void check_If_SID_Is_Only_Numbers() {
            assertEquals("Only numbers are allowed for SID field!",
                    student.register("Sam", "Tucker","12a456789"));
        }

        @Test
        @DisplayName("Student ID is exactly 9 digits in length")
        void check_If_SID_Is_9_Digits_In_Length() {
            assertEquals("SID must be exactly 9 digits in length!",
                    student.register("Sam", "Tucker","1123456789"));
        }

        @Test
        @DisplayName("Student ID exists in the file")
        void check_If_SID_Exists() {
            assertEquals("SID already exists in the system, please enter a different SID!",
                    student.register("Sam", "Tucker","122432434"));
        }

        @Test
        @DisplayName("Student ID does not exist in the file")
        void check_If_SID_Not_Exists() {
            assertEquals("Success",
                    student.register("Sam", "Tucker","123453783"));
        }

        @Test
        @DisplayName("Student first name consists of only letters")
        void check_If_Fname_Is_Only_Letters() {
            assertEquals("Characters ranging from a-z or A-Z are allowed for first name!",
                    student.register("S2m", "Tucker","123453783"));
        }

        @Test
        @DisplayName("Student last name consists of only letters")
        void check_If_Lname_Is_Only_Letters() {
            assertEquals("Characters ranging from a-z or A-Z are allowed for last name!",
                    student.register("Sam", "T2cker","123453783"));
        }
    }

    @DisplayName("Grouped tests for currently borrowed books by the student")
    @Nested
    class ViewBorrowedBooksTest {

        @Test
        @DisplayName("Student didn't borrow any books")
        void student_Didnt_Borrow_Any_Books() {
            student.setSid("123456745");
            assertEquals(new JSONArray(), student.getAllBorrowedBooks());
        }

        @Test
        @DisplayName("Student borrows a specific books")
        void student_Borrows_Books() {
            student.setSid("122432434");
            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "Claudio Esposito");
            book.put("Title", "Test");
            book.put("ID", 123456L);

            student.borrowABook(book);
            student.writeDataToFile();
            assertEquals(book.toString(), student.getABorrowedBook(123456L).toString());
        }

        @Test
        @DisplayName("Student borrows more than one item")
        void student_Has_Borrowed_Multiple_Books() {
            student.setSid("345432543");

            JSONObject book1 = new JSONObject();
            book1.put("Pages", 1);
            book1.put("Author", "Claudio Esposito");
            book1.put("Title", "Test");
            book1.put("ID", 123456L);

            JSONObject book2 = new JSONObject();
            book2.put("Pages", 1);
            book2.put("Author", "Claudio Esposito");
            book2.put("Title", "Test");
            book2.put("ID", 1234567L);

            JSONArray books = new JSONArray();
            books.add(book1);
            books.add(book2);

            student.borrowABook(book1);
            student.borrowABook(book2);
            student.writeDataToFile();
            assertEquals(books.toString(), student.getAllBorrowedBooks().toString());
        }
    }

    @DisplayName("Grouped tests for returning a book")
    @Nested
    class ReturnBookTest {
        @Test
        @DisplayName("Student returns a borrowed book, regardless if they borrowed multiple or just one")
        void return_Book() {
            //Make sure to remove the book from studentUnitTestData.json file for this student
            //and also update the studentLibrarianUnitTestData.json to remove the sid from waiting list array
            //before testing otherwise the test will result in undefined behavior.

            student.setSid("345432543");

            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "Claudio Esposito");
            book.put("Title", "Test");
            book.put("ID", 123456L);

            student.borrowABook(book);
            student.returnABook(book);
            student.writeDataToFile();
            assertNull(student.getABorrowedBook(123456L));
        }
    }

    @DisplayName("Grouped tests for borrowing a book")
    @Nested
    class BorrowBookTest {
        @Test
        @DisplayName("Student borrows a book, that is already borrowed by him/her.")
        void borrow_Already_Borrowed_Book() {
            student.setSid("234243563");

            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "Claudio Esposito");
            book.put("Title", "Test");
            book.put("ID", 123L);

            assertEquals("Book already borrowed!", student.borrowABook(book));
        }

        @Test
        @DisplayName("Student borrows a book, that is already part of waiting list.")
        void borrow_Already_On_Waiting_List_Book() {
            student.setSid("345432543");

            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "Claudio Esposito");
            book.put("Title", "Test");
            book.put("ID", 123456L);

            assertEquals("Book still in waiting list!",student.borrowABook(book));
        }

        @Test
        @DisplayName("Student borrows a book, that he/she hasn't borrowed yet. " +
                "The book is in stock so it will be added to borrowed books array.")
        void borrow_A_Book() {
            //this test will only work once, make sure to remove the book manually
            //from 123456767 books array in studentUnitTestData.json file before
            //running the test otherwise the test will fail.
            student.setSid("123456767");

            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "test");
            book.put("Title", "demo");
            book.put("ID", 1234567L);

            assertEquals("Book successfully borrowed!",student.borrowABook(book));
            student.writeDataToFile();
        }

        @Test
        @DisplayName("Student borrows a book, that he/she hasn't borrowed yet." +
                "The book is in stock so it will be added to waiting books array.")
        void wait_For_A_Book() {
            //this test will only work once, make sure to remove the book from 234243563
            //waiting array before running the test otherwise the test will fail
            student.setSid("234243563");

            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "Claudio Esposito");
            book.put("Title", "Test");
            book.put("ID", 123456L);

            assertEquals("Book put on waiting list!",student.borrowABook(book));
            student.writeDataToFile();
            assertNull(student.getABorrowedBook(123456L));
        }

        @Test
        @DisplayName("Student is assigned a book, that he/she was waiting on")
        void gets_Assigned_A_Book_That_Was_Being_Waited_On() {
            //this test will only work once, make sure to manually remove the book
            //from books array in 123456745 student from studentUnitTestData.json, add the book to
            //books array in 123456767 student from studentUnitTestData.json, otherwise the test will fail
            JSONObject book = new JSONObject();
            book.put("Pages", 1);
            book.put("Author", "test");
            book.put("Title", "demo");
            book.put("ID", 1234567L);

            student.setSid("123456745");
            assertEquals("Book put on waiting list!",student.borrowABook(book));
            student.writeDataToFile();

            student.setSid("123456767");
            student.returnABook(book);
            student.writeDataToFile();

            student.login("123456745");
            assertEquals(book,student.getABorrowedBook(1234567L));
        }
    }
}