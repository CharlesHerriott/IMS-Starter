Coverage: 82.6%
# Inventory Management System Project
The Inventory Management System allows for users to Create, Read, Update and Delete (CRUD) objects from SQL database tables. This achieved through the usage of JCDB and Java, which work in tandem to prepare and execute SQL queries into the connected database.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

In order to run, this application requires some mininum prerequisites. Java 14.0.x is required as a mininum version to run the application, which can be found at:

```
https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html
```
The application has been packaged into a fat jar file, allowing for immediate execution. This can be done through a command line interpreter such as, GitBash or CMD. GitBash can be downloaded at:
```
https://git-scm.com/downloads
```

In order to edit the application, or to view the unit tests a Java IDE is required. Eclipse is recommended for this. To download, go to:
```
https://www.eclipse.org/downloads/
```

### Installing

To install the application, follow these steps:
1. Create a file directory on your local machine, where you want to save the application to.

2. Right click within the file explorer and select GitBash here.

3. Clone the repository using this command:
```
git clone https://github.com/CharlesHerriott/IMS-Starter.git
```
This will clone the files from the GitHub repository into your chosen directory.


## Running the application
After installing, the application can now be executed. To do this, follow these steps:
1. Navigate to the file directory containing the root folder of the application.

2. Right click within the file explorer and select GitBash here.

3. Enter this command:
```
java -jar ims-0.0.1-jar-with-dependencies.jar
```
4. You will then be prompted with a menu. To select a choice, type in the relevant word.
```
Welcome to the Inventory Management System!
CUSTOMER: customer screen
ITEM: item screen
ORDER: order screen
STOP: exit
```
5. If CUSTOMER was selected, this screen would appear:
```
Customer Screen
CREATE: add a customer to the database
READ: display customers from the database
UPDATE: edit a customer in the database
DELETE: delete a customer in the database
```

## Running the tests
To run the application tests, open the application through a Java IDE (e.g. Eclipse).
Navigate to the IMS-Starter/src/test/java, and run this folder as "JUnit Test"

### Unit Tests 
The Unit tests perform tests on a per-method basis. They are used to test the highest amount of outcomes possible, and ensure the method returns what is expected. 
```
@Test
public void toStringTest(){
   private final String fname = "Charlie";
   private final String lname = "Herriott";
   private final Customer customer = new Customer("Charlie", "Herriott");
   assertEquals(expected, customer.toString();
}
```
The code snippet above shows the method in which unit tests ensure that the outcome of a method is the same as expected.

### Integration Tests 
Mockito is used for intergration testing. This allows for other objects and methods to be mocked with a pre-defined result.
This is then used to ensure that the method that is being tested returns the expected value.
```
@Test
	public void testCreateOrder() {
		// RESOURCES
		final Long customerId = 1L;
		final Order created = new Order(customerId);
		final String choice = "N";

		// RULES
		Mockito.when(utils.getLong()).thenReturn(customerId);
		Mockito.when(orderDAO.createUpdateOrder(Mockito.any(Order.class), Mockito.anyBoolean())).thenReturn(created);
		Mockito.when(utils.getString()).thenReturn(choice);

		// ACTIONS
		final Order result = orderController.create();

		// ASSERTIONS
		assertEquals(created, result);
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(orderDAO, Mockito.times(1)).createUpdateOrder(Mockito.any(Order.class), Mockito.anyBoolean());

	}
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Charlie Herriott** - *Continued work* - [Charlie Herriott](https://github.com/CharlesHerriott 

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

QA Consulting

