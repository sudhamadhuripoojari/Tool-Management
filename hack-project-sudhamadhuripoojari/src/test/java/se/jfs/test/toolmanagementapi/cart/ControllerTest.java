package se.jfs.test.toolmanagementapi.cart;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class ControllerTest {


    private static String cartId;
    @Value("${server.port}")
    private int port;



    @Autowired
    RestTemplate restTemplate;

    @BeforeAll
    static void initCart(@Autowired RestTemplate template, @Value("${server.port}") int port ) {
        String uri = "http://localhost:%s/api/carts".formatted(port);
        try {
            ResponseEntity<CartDTO> exchange = template.exchange(uri, HttpMethod.POST, HttpEntity.EMPTY, CartDTO.class);
            cartId = exchange.getBody().cartId();
        } catch (Exception e) {

        }

    }

    @Test
    @Order(1)
    void shouldCreateCartForPost(){
        String uri = "http://localhost:%s/api/carts".formatted(port);
        ResponseEntity<CartDTO> exchange = restTemplate.exchange(uri, HttpMethod.POST, HttpEntity.EMPTY, CartDTO.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(exchange.getHeaders().getFirst("location")).isNotNull();
        assertThat(exchange.getBody().cartId()).isNotNull();
        assertThat(exchange.getBody().products()).isEmpty();
        assertThat(exchange.getBody().totalPrice()).isEqualTo(0.0);

    }


    @Test
    @Order(2)
    void shouldGetCartByIdUsingHeaderLocation(){

        String host = "http://localhost:%s".formatted(port);

        ResponseEntity<CartDTO> created = restTemplate.exchange(host+"/api/carts", HttpMethod.POST, HttpEntity.EMPTY, CartDTO.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String location = created.getHeaders().getFirst("location");

        ResponseEntity<CartDTO> cart = restTemplate.getForEntity(host + location, CartDTO.class);
        assertThat(cart.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cart.getBody().cartId()).isEqualTo(created.getBody().cartId());
        assertThat(cart.getBody().cartId()).isNotNull();
        assertThat(cart.getBody().products()).isEmpty();
        assertThat(cart.getBody().totalPrice()).isEqualTo(0.0);

    }

    @Test
    @Order(3)
    void shouldAddProductToCart(){
        String uri = "http://localhost:%s/api/carts/%s/products".formatted(port, cartId);
        AddCartProductDTO productToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296390"
                , 2);
        ResponseEntity<CartDTO> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(productToAdd), CartDTO.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        CartDTO updatedCart = exchange.getBody();
        assertThat(updatedCart.products()).hasSize(1);
        assertThat(updatedCart.totalNumberOfItems()).isEqualTo(2);

    }

    @Test
    @Order(4)
    void shouldAddSecondProductToCart(){
        String createCartURI = "http://localhost:%s/api/carts".formatted(port);
        ResponseEntity<CartDTO> exchange = restTemplate.exchange(createCartURI, HttpMethod.POST, HttpEntity.EMPTY, CartDTO.class);
        CartDTO cart = exchange.getBody();
        String uri = "http://localhost:%s/api/carts/%s/products".formatted(port, cart.cartId());
        AddCartProductDTO firstProductToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296390"
                , 2);
        AddCartProductDTO secondProductToAdd = new AddCartProductDTO(
                "a10c9cf4-b67d-4db5-9678-7fdaa96f31d0"
                , 1);
        ResponseEntity<CartDTO> exchangeProduct1 = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(firstProductToAdd), CartDTO.class);
        ResponseEntity<CartDTO> exchangeProduct2 = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(secondProductToAdd), CartDTO.class);
        assertThat(exchangeProduct2.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        CartDTO updatedCart = exchangeProduct2.getBody();
        assertThat(updatedCart.products()).hasSize(2);
        assertThat(updatedCart.totalNumberOfItems()).isEqualTo(3);

    }

    @Test
    @Order(5)
    void shouldReturn404TryingToAddUnknownProductToCart(){
        String uri = "http://localhost:%s/api/carts/%s/products".formatted(port, cartId);
        AddCartProductDTO productToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296000"
                , 2);
        try {
            ResponseEntity<Void> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(productToAdd), Void.class);
            fail("Should throw client exception");
        } catch(HttpClientErrorException err) {
            assertThat(err.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }
    }

    @Test
    @Order(6)
    void shouldReturnNotAcceptableWhenTryingToAddNegativeQuantity(){
        String uri = "http://localhost:%s/api/carts/%s/products".formatted(port, cartId);
        AddCartProductDTO productToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296000"
                , -1);
        try {
            ResponseEntity<Void> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(productToAdd), Void.class);
            fail("Should throw client exception");
        } catch(HttpClientErrorException err) {
            assertThat(err.getStatusCode().value()).isGreaterThanOrEqualTo(400);

        }
    }

    @Test
    @Order(7)
    void shouldReturn404TryingToProductToNonExistentCart(){
        String uri = "http://localhost:%s/api/carts/%s/products".formatted(port, "62c2a4717471474feecbc4111");
        AddCartProductDTO productToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296390"
                , 1);
        try {
            ResponseEntity<Void> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(productToAdd), Void.class);
            fail("Should throw client exception");
        } catch(HttpClientErrorException err) {
            assertThat(err.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }


    @Test
    @Order(9)
    void shouldReturn204ForDeleteCart(){
        String uri = "http://localhost:%s/api/carts/%s".formatted(port, cartId);
        ResponseEntity<Void> delete = restTemplate.exchange(uri, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    @Order(10)
    void shouldReturn204ForNonExistentCart(){
        String uri = "http://localhost:%s/api/carts/%s".formatted(port,"0000000000000000");
        ResponseEntity<Void> delete = restTemplate.exchange(uri, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(11)
    void shouldAddSameProductToCart(){
        String createCartURI = "http://localhost:%s/api/carts".formatted(port);
        ResponseEntity<CartDTO> exchange = restTemplate.exchange(createCartURI, HttpMethod.POST, HttpEntity.EMPTY, CartDTO.class);
        CartDTO cart = exchange.getBody();
        String uri = "http://localhost:%s/api/carts/%s/products".formatted(port, cart.cartId());
        AddCartProductDTO firstProductToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296390"
                , 2);
        AddCartProductDTO secondProductToAdd = new AddCartProductDTO(
                "5f17847c-def9-4f17-8c99-be0829296390"
                , 1);
        ResponseEntity<CartDTO> exchangeProduct1 = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(firstProductToAdd), CartDTO.class);
        ResponseEntity<CartDTO> exchangeProduct2 = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(secondProductToAdd), CartDTO.class);
        assertThat(exchangeProduct2.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        CartDTO updatedCart = exchangeProduct2.getBody();
        assertThat(updatedCart.products()).hasSize(1);
        assertThat(updatedCart.totalNumberOfItems()).isEqualTo(3);

    }
}