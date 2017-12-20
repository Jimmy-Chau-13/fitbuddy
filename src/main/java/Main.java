

import app.auth.AuthController;
import app.db.DataBaseHelper;
import app.graph.GraphController;
import app.index.IndexController;

import app.workout.WorkOutController;
import app.util.Path;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.logging.Logger;

import static spark.Spark.*;



public class Main {

    //public static WorkOutDAO workoutDao;
    //private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws ClassNotFoundException {


        staticFileLocation("/public");
        port(getHerokuAssignedPort());

        new DataBaseHelper();

        // Serving Pages
        get(Path.Web.GET_INDEX_PAGE, (req,res) -> IndexController.serveIndexPage(req,res)
                ,new HandlebarsTemplateEngine());

        get(Path.Web.GET_PROFILE_PAGE, (req,res) -> WorkOutController.serveProfile(req,res)
                ,new HandlebarsTemplateEngine());


        // Handle Authentication
        post(Path.Web.DO_SIGNIN, (req,res) -> AuthController.handleSignIn(req,res));

        post(Path.Web.DO_AUTH, (req,res) -> AuthController.handleAuth(req,res));

        post(Path.Web.DO_SIGNUP, (req,res) -> AuthController.handleSignUp(req,res));

        post(Path.Web.DO_LOGOUT, (req,res) -> AuthController.handleLogout(req,res));

        // CRUD operations for work outs
        post(Path.Web.ADD_WORKOUT, (req,res) -> WorkOutController.handleAddWorkout(req,res));

        post(Path.Web.EDIT_WORKOUT, (req,res) -> WorkOutController.handleUpdateWorkout(req,res));

        post(Path.Web.DELETE_WORKOUT, (req,res) -> WorkOutController.handleDeleteWorkout(req,res));

        post(Path.Web.VIEW_WORKOUT, (req,res) -> WorkOutController.handleViewWorkout(req,res));

        post(Path.Web.GRAPH_WORKOUT, (req,res) -> GraphController.handleGraphWorkout(req,res));




    } //EOF MAIN



    public static int getHerokuAssignedPort() {
    //this will get the heroku assigned port in production

        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

} // EOF CLASS
