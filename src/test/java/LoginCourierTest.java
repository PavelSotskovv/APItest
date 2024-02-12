import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.StepsCourier;

import static constants.RandomDatas.*;
import static constants.UsingUrls.URL;

public class LoginCourierTest {
    StepsCourier stepsCourier;

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
        stepsCourier = new StepsCourier();
        stepsCourier.createCourier(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
    }

    @Test
    @DisplayName("Успешный вход курьера в систему")
    @Description("Когда мы вводим валидные пароль и логин, у нас отправляется запрос, который возвращается ID курьера")
    public void loginCourierSuccess() {
        Response loginResponse = stepsCourier.loginCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerAndPresenceId(loginResponse);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход в систему курьера с неправильным логином курьера")
    @Description("Создаем нового курьера, входим в систему с неправильным логином курьера и проверяем неудачный вход курьера, StatusCode=404")
    public void loginCourierWithIncorrectLoginFailed() {
        Response wrongLoginResponse = stepsCourier.loginCourier("wrongLogin", RANDOM_PASS);
        stepsCourier.checkAnswerWithWrongData(wrongLoginResponse);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход в систему курьера с неправильным паролем курьера")
    @Description("Создаем нового курьера, входим в систему с неправильным паролем курьера и проверяем неудачный вход курьера, StatusCode=404")
    public void loginCourierWithIncorrectPassFailed() {
        Response wrongPassResponse = stepsCourier.loginCourier(RANDOM_LOGIN, "987");
        stepsCourier.checkAnswerWithWrongData(wrongPassResponse);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход в систему курьера без логина курьера")
    @Description("Создаем нового курьера, входим без логина курьера и проверяем неудачный вход курьера, StatusCode=400")
    public void loginCourierWithoutLoginFailed() {
        Response withoutLoginResponse = stepsCourier.loginCourier("", RANDOM_PASS);
        stepsCourier.checkAnswerWithoutData(withoutLoginResponse);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }

    @Test
    @DisplayName("Неудачный вход в систему курьера без пароля курьера")
    @Description("Создаем нового курьера, входим без пароля курьера и проверяем неудачный вход курьера, StatusCode=400")
    public void loginCourierWithoutPassFailed() {
        Response withoutPassResponse = stepsCourier.loginCourier(RANDOM_LOGIN, "");
        stepsCourier.checkAnswerWithoutData(withoutPassResponse);
        Response responseDelete = stepsCourier.deleteCourier(RANDOM_LOGIN, RANDOM_PASS);
        stepsCourier.checkAnswerThenValidDeleting(responseDelete);
    }
}
