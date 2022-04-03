# EasyModeling

[![version][release-badge]][release-link]
[![maven][maven-badge]][search-maven]
[![license][license-badge]][license-2.0]

[![TEST][test-badge]][test-link]
[![Quality Gate Status][sonar-badge]][sonar-link]
[![Coverage][coverage-badge]][coverage-link]

A Java annotation processor that generates randomly populated objects for test use.

[release-badge]: https://img.shields.io/github/v/release/easymodeling/easy-modeling?color=informational&include_prereleases&label=latest%20release

[release-link]: https://github.com/easymodeling/easy-modeling/releases

[maven-badge]: https://img.shields.io/maven-central/v/io.github.easymodeling/easy-modeling?color=green

[license-badge]: https://img.shields.io/github/license/easymodeling/easy-modeling?color=yellow

[test-badge]: https://github.com/easymodeling/easy-modeling/actions/workflows/test.yml/badge.svg?branch=master

[test-link]: https://github.com/easymodeling/easy-modeling/actions/workflows/test.yml

[sonar-badge]: https://sonarcloud.io/api/project_badges/measure?project=easymodeling_easy-modeling&metric=alert_status

[sonar-link]: https://sonarcloud.io/summary/new_code?id=easymodeling_easy-modeling

[coverage-badge]: https://sonarcloud.io/api/project_badges/measure?project=easymodeling_easy-modeling&metric=coverage

[coverage-link]: https://sonarcloud.io/summary/overall?id=easymodeling_easy-modeling

## What is EasyModeling?

EasyModeling is a Java [annotation processor][java-atp-link] that generates randomly populated objects for test use. It
could save you from writing tedious code to prepare objects for tests, making your unit tests clearer and more readable.
The idea of EasyModeling comes from Martin Fowler's blog [ObjectMother][object-mother-link]. In addition to the concept
of ObjectMother, EasyModeling also provides builders of your models so that you can easily customize generated objects
for different test scenarios.

[java-atp-link]: https://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html

[object-mother-link]: https://martinfowler.com/bliki/ObjectMother.html

## What can EasyModeling do?

EasyModeling provides a set of APIs to help you generate objects for your test, including:

- An annotation `@Model` to tell EasyModeling for which classes you would like to generate objects.
- An annotation `@Field` and many annotation attributes, which is however totally optional, to customize the generated
  objects.
- A `next()` method for each class returns randomly populated objects.
- A `builder()` method for each class returns randomly populated builders of the objects.

### Simple Example

For example, if you have a class `Employee` involved in your test so that you want to generate objects for it, you can
firstly have a modeler configuration like this:

```java

@Model(type = Employee.class)
public class Modelers {
}
```

Then, EasyModeling will create a modeler named `EmployeeModeler` for you so that you can use the static `next()`
method to generate objects:

[//]: # (@formatter:off)
```java
Employee employee = EmployeeModeler.next();
```
[//]: # (@formatter:on)

For a specific test scenario, let's say for a test caring employee's age and marital status, by using static `builder()`
method, you have the chance to customize the generated objects:

[//]: # (@formatter:off)
```java
Employee employee = EmployeeModeler.builder().age(30).maritalStatus(SINGLE).build();
```
[//]: # (@formatter:on)

## Why EasyModeling?

EasyModeling starts with the factory pattern and the builder pattern. The main purpose of EasyModeling is to make unit
tests clearer and more readable. Let's assume we have a unit test for `annualLeave()` method of the `LeaveCalculator`
whose responsibility is to calculate the *total days of annual leave* of an employee from their *length of service*
at the company. The test could be written as follows:

[//]: # (@formatter:off)
```java
@Test
void should_get_28_days_for_employees_less_than_5_years_of_service() {

    // given (mock up employee details)
    Employee employee = new Employee(
            23212,                             // id
            "John",                            // first name
            "Smith",                           // last name
            LocalDate.of(1991, 1, 23),         // birthdate
            MaritalStatus.SINGLE,              // marital status
            Position.DEVELOPER,                // position
            List.of(                           // dependents
                    new Dependent(...),
                    new Dependent(...)
                    new Dependent(...)
            ),
            LocalDate.of(2017, 8, 9),          // date of joining    --> (1)
            ...
        );

    // when
    int annualLeave = leaveCalculator.annualLeave(employee);      // --> (2)

    // then
    assertEquals(annualLeave, 28);                                // --> (3)
}
```
[//]: # (@formatter:on)

Several pain points could be encountered when writing unit tests like above.

1. The only codes that describe the test scenario are the lines of `(1)`, `(2)` and `(3)`. Others are just helping mock
   up the context, which could not help us understand the test. In most cases, it's usual for these unnecessary mock-up
   codes to become extremely long.
2. Moreover, most of the mock-up would make the test harder to read. Even in a reasonably scaled system, developers will
   find they have to create hundreds of verbose mock-up codes like above, which makes our tests neither readable nor
   maintainable.
3. Even if the constructor in the mock-up part could be replaced by some techniques like Builder Pattern, it won't help
   reduce the complexity. And it also does not make too much sense to either allow some parameters of the constructor as
   nullable or create a builder for the `Employee` class, only for the convenience of the tests.
4. Regarding the maintainability, imagine we somehow changed any part of the parameter list of the constructor
   of `Employee` class, we would have to change all the tests as well.

**With the power of EasyModeling**, these problems could be solved significantly:

[//]: # (@formatter:off)
```java
@Test
void should_get_28_days_for_employees_less_than_5_years_of_service() {

    // given (mock up employee details)
    Employee employee = EmployeeModeler.builder()
                            .dateOfJoining(LocalDate.of(2017, 8, 9))
                            .build();

    // when
    int annualLeave = leaveCalculator.annualLeave(employee);

    // then
    assertEquals(annualLeave, 28);
}
```
[//]: # (@formatter:on)

## Requirements

Java 1.8 or later is required.

## Using EasyModeling

EasyModeling is available in [Maven Central][search-maven], so it's easy to ask build tools like Maven or Gradle to
integrate it into your projects.

[search-maven]: https://search.maven.org/search?q=g:io.github.easymodeling

### Gradle

For Gradle users, put the following into your build.gradle file:

[//]: # (@formatter:off)
```groovy
dependencies {
    ...
    testImplementation 'io.github.easymodeling:easy-modeling:0.1.0-Beta'
    testAnnotationProcessor 'io.github.easymodeling:easy-modeling-processor:0.1.0-Beta'
    ...
}
```
[//]: # (@formatter:on)

### Maven

For Maven-based projects, add the following to your POM file:

[//]: # (@formatter:off)
```xml
<dependencies>
    <dependency>
        <groupId>io.github.easymodeling</groupId>
        <artifactId>easy-modeling</artifactId>
        <version>0.1.0-Beta</version>
        <scope>test</scope>
    </dependency>
</dependencies>
...
<build>
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
            <source>1.8</source>
            <target>1.8</target>
            <annotationProcessorPaths>
                <path>
                    <groupId>io.github.easymodeling</groupId>
                    <artifactId>easy-modeling-processor</artifactId>
                    <version>0.1.0-Beta</version>
                </path>
            </annotationProcessorPaths>
        </configuration>
    </plugin>
</plugins>
</build>
...
```
[//]: # (@formatter:on)

### Note

Please be aware that EasyModeling is designed for test use, so think twice before using it in production. Using
EasyModeling in production will introduce a lot of side effects, such as:

- Security risks: Since EasyModeling generates randoms by using java.util.Random, which is not secure enough compared to
  more advanced random generators.
- Performance problem: EasyModeling is not optimized for performance. It generates objects by using reflection to
  achieve the convenience and simplicity of writing tests, regardless of the runtime performance.
- NPE risk: EasyModeling won't promise to populate all fields of the generated objects, although it always tries its
  best to do so. It's possible that some fields are not populated, which may cause NPE.

## Next Steps

Some next steps are in consideration to improve EasyModeling:

- Support some annotations from [Bean Validation 2.0][bean-validation-2.0] as the customizations of value ranges for
  generated objects.
- Groovy support.
- Support more widely used data types as basic types of objects population.

**Please feel free to [create an issues][raise-issues] if you have any more exciting ideas.**

[bean-validation-2.0]: https://beanvalidation.org/2.0/spec/

[raise-issues]: https://github.com/easymodeling/easy-modeling/issues/new?template=feature_request.yml

## License

EasyModeling is Open Source software licensed under the [APACHE LICENSE, VERSION 2.0][license-2.0].

[license-2.0]: https://www.apache.org/licenses/LICENSE-2.0.html
