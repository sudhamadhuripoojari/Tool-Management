[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XjvmvJsT)
# A database-driven Cart API

In this exercise, you will create storage for carts and use existing products in a product database in PostGres SQL. All the functionality will be exposed through an API with 4 endpoints, as outlined below. We have supplied you with a test suite that access those endpoints, so you need to name them correctly in order for the tests to work.
Our tests also rely on the correct test data being imported, this is done in the tests, and the db started with docker has them pre loaded.  


## Getting started

* Get and clone this repo.
* Implement the API and use spring-data to access the Databases
  * We have provided a starting skeleton for you with `CartApi.java`, `application.properties` for both main and tests
* Make sure all the test pass
  * Run tests
  * Solve tests
  * Refactor
  * Rinse and repeat
* Run your application and make sure it works as intended
  * In a new terminal window run: `docker compose up` to start the databases
    * use `docker ps`  to check everything is up & running
    * If you want to shut down `docker compose down`
  * Open Adminer (http://localhost:1234) or use IntelliJ to have a look through the product database.
    * **Adminer settings**
    * System: PostgreSQL
    * Server: postgres
    * Username: postgres
    * Password: postgres123
    * Database: postgres
    * **Intellij settings**
    * Datasource: PostgreSQL
    * Host: localhost
    * Port:5432 
    * Username: postgres
    * Password: postgres123
    * Database: postgres

  * Run the API for testing via PostMan or Curl
    * Run the application in intellij, or build it and run it from the command line like: 
      * `mvn clean package`
      * `java -jar  target/jfs-test-dbcartapi-1.0-SNAPSHOT.jar`


If you are interested in the docker setup for this test you can read more about that [here](./dockerSetup.md)

## Requirement Specification
Here are the endpoints you need to implement:

* POST `/api/carts/` - to create a new cart. When this endpoint is invoked you should:
  * Create a new cart in the database
    * create it in a table called `salt_carts`
    * store the CartProducts in a `cart_product` table
    * map the CartProducts to the Cart and products appropriately 
      * A Cart has _many_ CartProducts 
      * A CartProduct has _one_ Product and belongs to _one_ Cart. 
      * A Product can present in _many_ different CartProducts 
  * return a location header `/api/cart/:cartId` where the id is the id of the cart.
  * return the cart with the default cart structure initialised. Let the tests guide you...
  * return suitable status codes for success and failure
  * There is no request body in this request. An _empty_ cart is always created.

* GET `/api/carts/:cartId` - returns the cart data
  * Return the data in the following format, with the appropriate content type. This cart has two example products, but there might be 0.

  ```json
  {
    "cartid" : "82479462-af95-4b88-bcef-4bf8f4261a4a",
    "products": [
      { "productId": "3a9f1a05-390e-4109-8072-ac7a1caa7001", "name": "A key ring", "price": 0.85, "quantity": 2},
      { "productId": "fe3e8d33-a0bc-4606-b1b3-eb59b645b94b", "name": "Playing cards", "price": 4.85, "quantity": 4}
    ],
    "totalNumberOfItems" : 6,
    "totalPrice" : 21.1
  }
  ```

  * use suitable status codes for success and failure
    * for failure - return a body like this `{ message: 'A description of the error'}`

* POST `/api/carts/:cartId/products/` - add a product to the given cart
  * Post the id of the product and the quantity to the endpoint:

  ```json
  { "productId": "fe3e8d33-a0bc-4606-b1b3-eb59b645b94b", "quantity": 123 }
  ```

  * Get the cart from the database, using the `:cartId` parameter
  * Get the product information (name, description, price) using the product `id` in the posted body.
    * The valid products are initialized through a script  (`resources/data.sql`) and stored in salt_products, so you can pick up valid ids from that script. Or by opening the pgAdmin tool, and check the `public.salt_products` table.
  * Add the products to the cart products, with the quantity.
  * If the same product already exists in the cart, increase that items' quantity.
  * Re-calculate the `totalPrice` and `totalNumberOfItems`
  * Store the updated cart in the database
  * Return the updated cart data, using correct REST principles

* DELETE `/api/carts/:cartId` - delete the cart
  * Use the cartId in the request to remove the cart.
  * Apply proper rest principles.


## Handing in the solution

Commit your code and push it!

## Test and evaluation

We have supplied you with a few API tests which we will run against your code.

You should write your own tests - but remember that when you are working with tests that interact with a database you need to think about cleaning up the data.


### FAQ

> How do I know how many items are in stock so I don't add too many to the cart? 

The Salt products are made on demand so you can add as many items as you'd like.

> Should I regenerate the salt_products table when I start up my application

No. If you do that you lose the products and the tests will fail. Make sure your application doesn't recreate the schema everytime it starts up. There is a setting for that.

> I can save the cart but no cart Items are stored, what am I doing wrong? 

Read up on the cascade settings.  

