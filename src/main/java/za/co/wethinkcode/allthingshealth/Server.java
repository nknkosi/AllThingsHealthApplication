package za.co.wethinkcode.allthingshealth;




import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import za.co.wethinkcode.allthingshealth.app.DefaultAccessManager;
import za.co.wethinkcode.allthingshealth.app.Sessions;
import za.co.wethinkcode.allthingshealth.home.HomeController;
import za.co.wethinkcode.allthingshealth.login.LoginController;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Server {
    private static final int DEFAULT_PORT = 7070;
    private static final String STATIC_DIR = "/html";
    private static final String STATIC_IMAGES_DIR = "/images";

    public static void main(String[] args) {
        Javalin app = getInstance();
        app.start(DEFAULT_PORT);
    }

    public static Javalin getInstance() {
        configureThymeleafTemplateEngine();
        Javalin server = createAndConfigureServer();
        setupRoutes(server);
        return server;
    }


    private static void configureThymeleafTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateEngine.setTemplateResolver(templateResolver);

        templateEngine.addDialect(new LayoutDialect());
        JavalinThymeleaf.configure(templateEngine);
    }

    private static void setupRoutes(Javalin server) {
        server.routes(() -> {
            loginAndLogoutRoutes();
            homePageRoute();
        });
    }

    private static void homePageRoute(){
        get(HomeController.PATH,HomeController::renderHomePage);
    }

    private static void loginAndLogoutRoutes() {
        post(LoginController.LOGIN_PATH,LoginController::handleLogin);
        get(LoginController.LOGOUT_PATH,LoginController::handleLogout);
    }



    @NotNull
    private static Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            config.addStaticFiles(STATIC_DIR, Location.CLASSPATH);
            config.addStaticFiles(STATIC_IMAGES_DIR, Location.CLASSPATH);
            config.sessionHandler(Sessions::nopersistSessionHandler);
            config.accessManager(new DefaultAccessManager());
        });
    }

}
