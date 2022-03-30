package za.co.wethinkcode.allthingshealth.login;

import io.javalin.http.Context;
import za.co.wethinkcode.allthingshealth.app.db.DataRepository;
import za.co.wethinkcode.allthingshealth.app.model.Person;
import za.co.wethinkcode.allthingshealth.home.HomeController;

public class LoginController {
    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";
    public static final String ROOT_PATH = "/index.html";

    public static void handleLogin(Context context) {
        String username = context.formParam("email");

        if (username == null) {
            context.redirect(ROOT_PATH);
            return;
        }
        final Person person = DataRepository.getInstance().addPerson(new Person(username));
        context.sessionAttribute("user", person);
        context.redirect(HomeController.PATH);
    }

    public static void handleLogout(Context context) {
        context.sessionAttribute("user", null);
        context.redirect(ROOT_PATH);
    }
}
