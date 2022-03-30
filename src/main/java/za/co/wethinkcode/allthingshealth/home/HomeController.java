package za.co.wethinkcode.allthingshealth.home;

import io.javalin.http.Context;
;

public class HomeController {

    public static final String PATH = "/home";

    public static void renderHomePage(Context context){

        context.render("home.html");
    }
}
