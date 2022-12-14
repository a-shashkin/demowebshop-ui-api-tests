import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.is;

public class DemowebshopTest {

    @Test
    void addToEmptyCartTest() {
        Response response =
        given().
                contentType("application/x-www-form-urlencoded; charset=UTF-8").
                header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/105.0.0.0 Safari/537.36").
                body("product_attribute_74_5_26=81&product_attribute_74_6_27=83&product_attribute_74_3_28=86&addtocart_74.EnteredQuantity=1").
        when().
                post("https://demowebshop.tricentis.com/addproducttocart/details/74/1").
        then().
                statusCode(200).
                extract().response();

        String message = response.path("message");
        Boolean success = response.path("success");
        String updatetopcartsectionhtml = response.path("updatetopcartsectionhtml");

        assertEquals("The product has been added to your <a href=\"/cart\">shopping cart</a>", message);
        assertEquals(true, success);
        assertEquals("(1)", updatetopcartsectionhtml);
    }

    @Test
    void unregisteredUserVote() {
        given().
                contentType("application/x-www-form-urlencoded; charset=UTF-8").
                header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/105.0.0.0 Safari/537.36").
                body("pollAnswerId=1").
        when().
                post("https://demowebshop.tricentis.com/poll/vote").
        then().
                statusCode(200).
                body("error", is("Only registered users can vote."));
    }

    @Test
    void removeFromCartTest() {
        given().
                contentType("multipart/form-data; boundary=----WebKitFormBoundaryufsLp4KPpbjV0c4E").
                header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/105.0.0.0 Safari/537.36").
                config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT))).
                body("------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"removefromcart\"\n" +
                        "\n" +
                        "2642767\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"itemquantity2642767\"\n" +
                        "\n" +
                        "1\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"updatecart\"\n" +
                        "\n" +
                        "Update shopping cart\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"discountcouponcode\"\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"giftcardcouponcode\"\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"CountryId\"\n" +
                        "\n" +
                        "0\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"StateProvinceId\"\n" +
                        "\n" +
                        "0\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E\n" +
                        "Content-Disposition: form-data; name=\"ZipPostalCode\"\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundaryufsLp4KPpbjV0c4E--").
                when().
                post("https://demowebshop.tricentis.com/cart").
                then().
                statusCode(200);
    }
}
