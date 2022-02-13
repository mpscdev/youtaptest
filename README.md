# Getting Started

### About the API

* This application provides an API to get the User Contact details through the following endpoint:

  ```http://{baseurl}/getusercontacts```

  It supports the following optional query parameters to search for user contacts by ```id``` and/or ```username```, for example

  ```http://{baseurl}/getusercontacts?id=1```

  ```http://{baseurl}/getusercontacts?username=Bret```

  ```http://{baseurl}/getusercontacts?id=1&username=Bret```

  ```http://{baseurl}/getusercontacts?id=1&id=2```

* It uses Spring ```webflux```, ```reactor test```, and ```lombok``` as the core dependencies.
* Please refer to ```UserContactControllerTests``` for controller tests.
* Since the data source API already supports search/filter through query parameters, this is utilised instead of getting all the Users from the list and manually filtering out the result.

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.3/maven-plugin/reference/html/)

