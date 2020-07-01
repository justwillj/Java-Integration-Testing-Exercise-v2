# Java Integration Testing Template

_Updated project that utilizes the latest solution to the Vehicle/Reviews project as a starting point for the Integration Testing exercise._

### Database Connection

Although this project still references PostgresSQL, the application.yml has been renamed to prod_application.yml

Additionally, the H2 database has been added as a dependency.  When the project starts and Spring Data cannot locate
the application.yml file, it defaults to the H2 database in memory and all of the tables and data are created
using that instance

### Running Tests

To run tests, right-click on the controllers package in the test folder and select **_Run Tests with Coverage_** 


