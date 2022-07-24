> #### Annotations

- @Stateless - Shared for every client and does not containe any state. One-to-many relnationship: client to bean.
- @Stateful - Unique for every client and capable of maintaining the conversational state with the client or use case. One to one relationship client to bean.
- @Singleton - Shared for every client and capable of maintaining state at an application level. Used across all clients.
- @MessageDriven - To handle the incoming message, we must implement the onMessage() method of the MessageListener interface and annotate the class with @MessageDriven. They look and feel similar to stateless session beans, but can not be directly access by the client.

- @Schedule - Java library provides various classes to schedule a thread or task that executes at a certain period of time once or periodically at a fixed interval and they are listed below.
- @Asynchronous - An asynchronous bean is a Java™ object or enterprise bean that can run asynchronously by a Java Platform, Enterprise Edition (Java EE) application, using the Java EE context of the asynchronous bean creator.
- @Startup - A startup service can be used to eagerly initialize some resources in your application or in external resources. Thanks to Java EE 6 addition, you don’t need anymore vendor solutions which allow to create startup classes. Just plug in a Bean which is tagged as @Startup and @Singleton

- @Transactional - Transaction management for POJOs (for example Seam, Spring/Hibernate).
- @TransactionAttribute - Transaction management for EJB3 beans.

- @ConcurrencyManagement - By default, every Stateful and Singleton EJB has container managed concurrency
- @Lock - The Lock annotation may have a couple of distinct values: READ and WRITE. Methods annotated with READ lock type may be accessed concurrently by any arbitrary number of clients. WRITE lock type methods are exclusive, ie. if a WRITE locked method is being accessed at a given moment, no other method - READ or WRITE - may be accessed until the method execution completes
- @AccessTimeout - We end this section mentioning the access timeout configuration. One may additionally configure the amount of time that a client request will wait for concurrent method access, before giving up. Once the timeout is reached, the container will throw an exception which will be propagated to the calling client. The timeout is configured by the @AccessTimeout annotation, which may be defined at both class and method level 

- @Remote - When your EJB and its clients will be in a distributed environment - meaning EJBs and clients will reside on separate Java virtual machines. Example : EJBs hosted on a WebSphere Application Server and Servlets that consume EJB APIs hosted on a Tomcat server.
- @Local - Only when it is guaranteed that other enterprise beans or clients will only address the bean within a single JVM. Example, EJBs as well as the Servlets deployed on the same WebSphere server.
- @LocalBean - Is almost same as local client view, but there are differences. Your bean class is not required to implement client view interfaces in this case. All public methods of the bean class are automatically exposed to the caller.

- @AssertTrue - Must return true
- @DecimalMax - Maximum value of decimal field
- @Fututre - Date must be in future
- @Max(100) - Max value
- @NotNull - Can not be null
- @Pattern - Must match regex
- @Size - The size must bee
- @Digits - Must be a digit

> #### Concurrency management

The @ConcurrencyManagement annotation takes a javax.ejb.ConcurrencyManagementType value. The options are:
- ConcurrencyManagementType.CONTAINER for container-managed concurrency.

To specify the access level to each of the singleton’s business methods, we'll use javax.ejb.Lock annotation. javax.ejb.LockType contains the values for the @Lock annotation. javax.ejb.LockType defines two values:
- LockType.WRITE – This value provides an exclusive lock to the calling client and prevents all other clients from accessing all methods of the bean. Use this for methods that change the state of the singleton bean.
- LockType.READ – This value provides concurrent locks to multiple clients to access a method. Use this for methods which only read data from the bean.

Use the @AccessTimeout annotation to define the number of milliseconds method times-out. After the timeout, the container throws a javax.ejb.ConcurrentAccessTimeoutException and the method execution terminates.

- ConcurrencyManagementType.BEAN for bean-managed concurrency.

Unless concurrency is implemented by the developer, all methods are accessible to all clients simultaneously. Java provides the synchronization and volatile primitives for implementing concurrency.

Next, we'll write the setStates() method which changes the state of the bean using synchronized keyword

The getStates() method doesn't change the state of the Bean and so it doesn't need to use the synchronized keyword.

> #### EJB beans

- **Session bean** - Contains business logic that models an action or use case
	- **Stateful** - Unique for every client and capable of maintaining the conversational state with the client or use case
	- **Stateless** - Shared for every client and does container any state
	- **Singleton** - Shared for every client and capable of mainting state at an application level
- **Message driven bean** - Bean that processes messages sent by other components

> #### Stateless vs Stateful vs Singleton

| 						 |Statefull 	    					    |Stateless 	  							    |Singleton 								 |
|------------------------|------------------------------------------|-------------------------------------------|----------------------------------------|
|Client bean instances   |One to One relationship: client to bean   |One to many relationship: client to bean   |Many to One relationship: client to bean|
|State  		    	 |Holds a state 					 	    |Doesnt maintain a state					|Executed once per application lifetime  |
|Removed     	 		 |Removed when the client session terminated|No state removal							|Removed when the application terminates |


> #### Maven scopes

There are two types of dependencies in Maven: direct and transitive. On the other hand, transitive dependencies are required by direct dependencies. Maven automatically includes required transitive dependencies in our project. We can list all dependencies including transitive dependencies in the project using mvn `dependency:tree` command. They also modify the classpath for different build tasks. Maven has six default dependency scopes.

- **Compile** - This is the default scope when no other scope is provided. Dependencies with this scope are available on the classpath of the project in all build tasks. They are also propagated to the dependent projects.

```xml
<dependency>
    <groupId>commons-lang</groupId>
    <artifactId>commons-lang</artifactId>
    <version>2.6</version>
</dependency>
```

- **Provided** - We use this scope to mark dependencies that should be provided at runtime by JDK or a container. A good use case for this scope would be a web application deployed in some container, where the container already provides some libraries itself. For example, this could be a web server that already provides the Servlet API at runtime.

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
```

- **Runtime** - The dependencies with this scope are required at runtime. But we don't need them for the compilation of the project code. Because of that, dependencies marked with the runtime scope will be present in the runtime and test classpath, but they will be missing from the compile classpath.

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
    <scope>runtime</scope>
</dependency>
```

- **Test** - We use this scope to indicate that dependency isn't required at standard runtime of the application but is used only for test purposes. Test dependencies aren't transitive and are only present for test and execution classpaths.

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

Each dependency scope affects transitive dependencies in its own way. This means that different transitive dependencies may end up in the project with different scopes. However, dependencies with scopes provided and test will never be included in the main project.

Let's take a detailed look at what this means:
- For the compile scope, all dependencies with runtime scope will be pulled in with the runtime scope in the project, and all dependencies with the compile scope will be pulled in with the compile scope in the project.
- For the provided scope, both runtime and compile scope dependencies will be pulled in with the provided scope in the project.
- For the test scope, both runtime and compile scope transitive dependencies will be pulled in with the test scope in the project.
- For the runtime scope, both runtime and compile scope transitive dependencies will be pulled in with the runtime scope in the project.

|		     	 |compile-classpath     |test-classpath 	|runtime-classpath 	|
|----------------|----------------------|-------------------|-------------------|
|compile   	     |🟢  			   		|🟢 					|🟢 					|
|test   	     |🔴  			   		|🟢 					|🔴 					|
|runtime   	     |🔴  			   		|🟢 					|🟢 					|
|provided 	     |🟢  			   		|🟢 					|🔴 					|
|system   	     |🟢  			   		|🟢 					|🔴 					|
|import   	     |No change		   		|No change			|No change			|

> #### Packing types

Maven offers many default packaging types and also provides the flexibility to define a custom one.

- **jar** - is one of the most popular packaging types. Projects with this packaging type produce a compressed zip file with the .jar extension. It may include pure Java classes, interfaces, resources, and metadata files.

```xml
<packaging>jar</packaging>
```

- **war** - Simply put, a web application archive – or war – contains all files related to a web application. It may include Java servlets, JSPs, HTML pages, a deployment descriptor, and related resources. Overall, war has the same goal bindings as a jar, but with one exception —the package phase of the war has a different goal, which is war.

```xml
<packaging>war</packaging>
```

- **ear** - Enterprise application archive – or ear – is a compressed file that contains a J2EE application. It consists of one or more modules that can be either web modules (packaged as a war file) or EJB modules (packaged as a jar file) or both of them.

```xml
<packaging>ear</packaging>
```

- **pom** - Among all packaging types, pom is the simplest one. It helps to create aggregators and parent projects. An aggregator or multi-module project assembles submodules coming from different sources. These submodules are regular Maven projects and follow their own build lifecycles. The aggregator POM has all the references of submodules under the modules element.

```xml
<packaging>pom</packaging>
```

- **ejb** - Enterprise Java Beans – or ejb – help to create scalable, distributed server-side applications. EJBs often provide the business logic of an application. A typical EJB architecture consists of three components: Enterprise Java Beans (EJBs), the EJB container, and an application server.

```xml
<packaging>ejb</packaging>
```

> #### Wildfly setup

```bash
# Download Wildfly
wget https://download.jboss.org/wildfly/24.0.1.Final/wildfly-24.0.1.Final.zip

# Switch to Wildfly directory
cd WILDFLY_HOME

# Start server in standalone mode
standalone.bat

# Connect to management cli
jboss-cli.bat --connect

# Download and deploy the driver
deploy "\my\path\mysql-connector-java-8.0.29.jar"

# Check installed drivers
/subsystem=datasources:installed-drivers-list

# Show available datasources
/subsystem=datasources:read-resource(recursive=true)

# Create new data source
data-source add --name=mysqlDataSource --jndi-name=java:/jdbc/mysqlDataSource --driver-name=mysql-connector-java-8.0.29.jar --connection-url=jdbc:mysql://127.0.0.1:3306/ejbExamples --user-name=root --password=root

# Check if data source is available
/subsystem=datasources/data-source=mysqlDataSource:test-connection-in-pool

# Restart running server
reload
```
