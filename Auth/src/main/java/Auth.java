import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;

public class Auth {
    private String login;
    private String password;
    private Response url;

    public Auth() {
    }
    public Auth(Response url) {
        this.url = url;
    }

    public List<String> getToken(String getURL) throws IOException {
        Response response = RestAssured.get(getURL).then().extract().response();
//        System.out.println(response.body().prettyPrint());
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element tokenNameInput = document.select("input[name=_token]").first();
        String xsrfToken = response.getCookie("XSRF-TOKEN");
        String laravelSession = response.getCookie("laravel_session");


       String token = tokenNameInput.attr("value");
       return Arrays.asList(token, xsrfToken, laravelSession);
    }

    public String getXSRFToken(){

        return url.getCookie("XSRF-TOKEN");
    }

    public Response getUrl() {
        return url;
    }
    public void setUrl(Response url) {
        this.url = url;
    }
}
