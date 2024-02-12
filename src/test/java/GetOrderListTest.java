import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.StepsOrder;

import static constants.UsingUrls.URL;


public class GetOrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Список заказов возвращается в ответе (в body)")
    @Description("Список всех заказов системы как файл json")
    public void getOrderListNotNull() {
        StepsOrder stepsOrder = new StepsOrder();
        Response response = stepsOrder.getOrdersList();
        stepsOrder.checkOrderListNotNullNew(response);
    }
}
