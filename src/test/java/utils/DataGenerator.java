package utils;


import com.github.javafaker.Faker;
import data.ClientInfo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {


    // спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    @UtilityClass
    public static class Authorization {
        public static ClientInfo clientInfo(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            ClientInfo clientInfo = new ClientInfo(
                    faker.name().username(),
                    faker.internet().password(8, 16, false, false),
                    status);
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(clientInfo) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
            return clientInfo;
        }


        public static String generateInvalidLogin(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return faker.name().username();
        }


        public static String generateInvalidPassword(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return faker.internet().password(8, 16, false, false);
        }
    }

}





