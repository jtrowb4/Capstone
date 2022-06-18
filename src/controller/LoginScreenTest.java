package controller;
import dao.UserDAO;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginScreenTest {
    Exception exception;
    User user;
    String userName = "admin";
    String password = "admin";

    @BeforeAll
    public void startTest() {
        try {
            user = UserDAO.getUserLogin(userName);
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        }
    }

        @Test
        public void ReturnTheUser() {
        assertNotNull(user);
        }

        @Test
        public void UserAuthorized() {
        assertEquals(user.getEmployeeName(), userName);
        assertEquals(user.getPassword(), password);
        }

        @Test
        public void ExceptionDidNotOccur(){
        assertNull(exception);
        }

}