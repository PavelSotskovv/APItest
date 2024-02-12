import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.StepsCourier;
import static constants.RandomDatas.*;
import static constants.UsingUrls.URL;

public class CreatingCourierTest {

    StepsCourier stepsCourier;

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
        stepsCourier = new StepsCourier();
    }


    @Test
    @DisplayName("Создание нового курьера")
    @Description("Создание нового курьера с корректными данными и проверка факта создания курьера")
    public void creatingCourierPositive() {
        Response responseCreate = stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        stepsCourier.checkAnswerValidRegistration(responseCreate);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Создание существующего курьера")
    @Description("Проверка овтета (status code and body) когда мы пытаемся создать идентичного (уже существующему) курьера")
    public void creatingIdenticalCouriersConflict() {
        stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        Response responseIdentical = stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        stepsCourier.checkAnswerReuseRegistrationData(responseIdentical);
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Создание курьера с существующим логином и паролем и проверка ответа от сервера")
    public void creatingCourierWithExistingLoginConflict() {
        stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        Response responseExisting = stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        stepsCourier.checkAnswerReuseRegistrationData(responseExisting);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера без логина и проверка ответа от сервера")
    public void creatingCourierWithoutLoginBadRequest() {
        Response responseWithoutLogin = stepsCourier.createCourier("", RANDOM_PASS, RANDOM_NAME);
        stepsCourier.checkAnswerWithNotEnoughRegData(responseWithoutLogin);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера без пароля и проверка ответа от сервера")
    public void creatingCourierWithoutPasswordBadRequest() {
        Response responseWithoutPass = stepsCourier.createCourier(RANDOM_LOGIN, "", RANDOM_NAME);
        stepsCourier.checkAnswerWithNotEnoughRegData(responseWithoutPass);
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Создание курьера без имени и проверка ответа от сервера")
    public void creatingCourierWithoutNamePositive() {
        Response responseWithoutName = stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, "");
        stepsCourier.checkAnswerValidRegistration(responseWithoutName);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

}
