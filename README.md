# Automation framework for QA interview on CyberCube
## About the project
This automation framework was created to fill the requirements listed in the technical challenge for job interview in CyberCube.
For this application, I decided to take the approach of a medium reliable application where the level of confidence and seniority of the developers is not particularly high, but also not completely senior team.
But since there's no documentation, it is hard to define what is a bug or not, except for the clear ones. I'll talk a bit more about it in the test results section.

## Technical perspective:

This project is a Java-based application that uses Maven for dependency management. It includes tests written in Cucumber for behavior-driven development (BDD).


## Setup

### Prerequisites

- Java
- Maven

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Run `mvn install` to install the dependencies

## Usage

This project has one runner for each module (UI, Api) and a unified one. To run them, you can use the following commands in the root folder:

Ui Runner:
```bash
mvn test -Dtest=RunnerUi
```
Api Runner:
```bash
mvn test -Dtest=RunnerApi
```
Unified Runner:
```bash
mvn test -Dtest=RunnerTest
```

##Reports:

After running the tests you will find the reports (html-report and json report) in the target folder.

![image](https://github.com/filipeferreira86/-CyberCube/assets/30532661/df02faad-7342-4149-99fd-b39da2819813)


## Test cases

The test cases can be found in the feature files under \resources\features\ui and \resources\features\api

![image](https://github.com/filipeferreira86/-CyberCube/assets/30532661/1a5af132-723a-43d7-ac3e-0a0a42ad1f61)


## Features

- API testing using RestAssured
- BDD tests written in Cucumber

## License

[MIT](https://choosealicense.com/licenses/mit/)

# Built with

This project was performed using Java as the programming language. I used Selenium Webdriver for the FrontEnd component and RestAssured for the BackEnd. In both cases, the framework used to orchestrate the tests was Cucumber.
The reason for using Cucumber is the easy approach for creating the automation at the same time the documentation is made. With the lack of documentation from the application, it seemed that the right approach was to document the tests whilst creating the automation.

# Functional perspective:

## Ui testing:

Application tested: <a href=https://www.saucedemo.com/> SauceDemo </a>
Main functionality: It is a test application that simulates a web store with login products and a checkout page.
Journey: If the client successfully login he/she is redirected to an inventory page that lets him/her select products either from the main page or on a detail page for the product. The items selected are added to his/her cart and after reviewing the items he/she can choose to follow to check out. Personal data is required before checkout and, after providing it, he/she is redirected to an overview page where the client can choose to finish the checkout.

Considering the diagram below, the main path of the client is to log in, add items to the cart, and proceed to checkout. The test cases are created with this in mind, but also the variations and possibilities, such as changing filters, going back and others.

![image](https://github.com/filipeferreira86/-CyberCube/assets/30532661/22a70977-9fe5-42a2-8a62-7385891a18d4)



## API testing

For the API tests, all endpoints are tested and, for as much as possible, they are covered considering the level of confidence mentioned above.

Application tested: <a href=https://petstore.swagger.io/> Backend for a petstore</a>
Main functionalities: It seems to be a partial backend for a pet shop store where you can manage pets to sell, orders and users of the application. There are 3 main endpoints but they don't seem to be correctly connected. It's possible to add pictures for a nonexistent pet, add orders for pet IDs that don't exist, etc. Also, some common behaviours cannot be observed here.
Journey: 
  * Pets: After pets are added to the post method, it should be possible to add pictures through the corresponding endpoint. It's also possible to change the data of the pet using the method PUT with the correct pet ID and delete it as well. It's possible to query the pets either by pet ID or by pet status. Even though the documentation says that the status should be an enum of 3 strings ([ available, pending, sold ]), this is not followed by the endpoint, nor by the DB. It's possible to create an arbitrary number of states. The only requirement is that it needs to be a string. In case any other format is sent, it is parsed to a string.
  * Orders: Since there are pets available to sell, it should be possible to create a new order for selling those pets. It is also possible to update or delete the order in the respective endpoint if the correct order ID is provided. In update cases, even if the incorrect ID is provided, a new register is created with the updated data. The same happens with pets updates and user updates. It's also possible to query the number of pets per status.
  * Users: The application allows the creation of users, who can be customers or store employees. Users can be created, updated, and deleted. They can also log in and log out of the system. The user data includes username, first name, last name, email, password, phone, and user status. The user status is an integer that should be used to determine if the user is enabled or not, but there's no documentation about what values it should take.

## Test Results

### UI Testing

The UI tests covered the main user journey from login to checkout. The tests also covered edge cases such as invalid login, adding and removing items from the cart, and navigating back and forth between pages. The tests revealed a few issues with the UI, such as inconsistent behaviour when adding items to the cart and some minor layout issues.

### API Testing

The API tests covered all the endpoints and their possible responses. The tests revealed several issues with the API, such as the ability to create pets, orders, and users with invalid data, and the lack of proper error handling. The tests also revealed that the API does not enforce the documented constraints on the pet status and user status fields.

## Conclusion

This project provided valuable insights into the quality of the SauceDemo and Petstore applications. The tests revealed several issues that need to be addressed to improve the reliability and usability of these applications. The use of Cucumber for test orchestration proved to be effective, as it allowed clear test documentation along with the automation. However, the lack of documentation for the Petstore API posed a challenge, as it made it difficult to determine the expected behaviour of the API.

## Future Work

Future work on this project could include expanding the test coverage to include more edge cases, improving the test reporting to provide more detailed information about the test results, and integrating the tests into a continuous integration/continuous deployment (CI/CD) pipeline to enable automated testing as part of the development process.
