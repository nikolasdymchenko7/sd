import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import jdk.jfr.ContentType;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.swing.text.Document;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.*;

public class JavaMain {
    public static void main(String[] args) throws IOException {
        String getURL = "https://tickets.dev.flcn.pro/login";
        Auth auth = new Auth();

        //System.out.println(auth.getToken("input[name=_token]", getURL));

        List<String> params = auth.getToken(getURL);
        params.forEach(System.out::println);

        String myLongUrl = "https://tickets.dev.flcn.pro/filter?draw=1&columns%5B0%5D%5Bdata%5D=id&columns%5B1%5D%5Bdata%5D=title&columns%5B2%5D%5Bdata%5D=ticket_number&columns%5B3%5D%5Bdata%5D=topic&columns%5B4%5D%5Bdata%5D=department&columns%5B5%5D%5Bdata%5D=notes&columns%5B6%5D%5Bdata%5D=from&columns%5B7%5D%5Bdata%5D=ib_name&columns%5B8%5D%5Bdata%5D=brand&columns%5B9%5D%5Bdata%5D=assigned_to&columns%5B10%5D%5Bdata%5D=last_activity&columns%5B11%5D%5Bdata%5D=created_at&order%5B0%5D%5Bcolumn%5D=10&order%5B0%5D%5Bdir%5D=desc&start=0&length=10&search%5Bvalue%5D=&segment=%2Fticket%2Finbox&_=1628583519513";
        Response response = RestAssured.given()
                .param("email", "user@site.com")
                .param("password", "test12345")
                .param("_token", params.get(0))
                .header("XSRF-TOKEN", params.get(1))
                .header("laravel_session", params.get(2))
                .redirects().follow(true)
                .post(getURL)
                .then().statusCode(302).extract().response();

        Response responseGetTickets = RestAssured.given()
                .param("email", "user@site.com")
                .param("password", "test12345")
                .param("_token", params.get(0))
                .header("XSRF-TOKEN", params.get(1))
                .header("laravel_session", params.get(2))
                .get(myLongUrl)
                .then().statusCode(200).extract().response();

        System.out.println(response.body().prettyPrint());

        /*given().urlEncodingEnabled(true)
                .param("email", "admin")
                .param("password", "test12345")
                .param("_token", auth.getToken("input[name=_token]"))
                .header()*/

        //Response response = given().post(

    }
}
