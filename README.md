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
could save you from writing tedious code to prepare objects under test, making your unit tests clearer and more
readable. The idea of EasyModeling comes from Martin Fowler's blog [ObjectMother][object-mother-link]. In addition to
the concept of ObjectMother, EasyModeling also provides builders of your models so that you can easily customize
generated objects for different test scenarios.

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

For example, if you have a class `Employee` under test so that you want to generate objects for it, you can firstly have
a modeler configuration like this:

```java

@Model(Employee.class)
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

## Requirements

Java 1.8 or later is required.

## Using EasyModeling

EasyModeling is available in [Maven Central][search-maven], so it's easy to ask build tools like Maven or Gradle to
integrate it into your projects.

[search-maven]: https://search.maven.org/search?q=g:io.github.easymodeling

### Maven

For Maven-based projects, add the following to your POM file:

[//]: # (@formatter:off)
```xml
<dependencies>
    <dependency>
        <groupId>io.github.easymodeling</groupId>
        <artifactId>easy-modeling</artifactId>
        <version>0.1.0-Alpha1</version>
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
                    <version>0.1.0-Alpha1</version>
                </path>
            </annotationProcessorPaths>
        </configuration>
    </plugin>
</plugins>
</build>
...
```
[//]: # (@formatter:on)

### Gradle

For Gradle users, put the following into your build.gradle file:

[//]: # (@formatter:off)
```groovy
dependencies {
    ...
    testImplementation 'io.github.easymodeling:easy-modeling:0.1.0-Alpha1'
    testAnnotationProcessor 'io.github.easymodeling:easy-modeling-processor:0.1.0-Alpha1'
    ...
}
```
[//]: # (@formatter:on)

### Note

Please be aware that EasyModeling is designed for test use, so think twice before using it in production. Using
EasyModeling in production will introduce a lot of side effects, such as:

- Security risks: Since EasyModeling generates randoms by using `java.util.Random`, which is not secure enough compared
  to more advanced random generators.
- Performance: EasyModeling is not optimized for performance. It generates objects by using reflection to achieve the
  convenience and simplicity of writing tests, regardless of the runtime performance.
- NPE risk: EasyModeling won't promise to populate all fields of the generated objects, although it always tries its
  best to do so. It's possible that some fields are not populated, which may cause NPE.

## Next Steps

Some next steps are in consideration to improve EasyModeling:

- Support some annotations from [Bean Validation 2.0][bean-validation-2.0] as the customizations of value ranges for
  generated objects.
- Groovy support.
- Support more widely used data types as basic types of objects population.

**Please feel free to [raise issues][raise-issues] if you have any more exciting ideas.**

[bean-validation-2.0]: https://beanvalidation.org/2.0/spec/

[raise-issues]: https://github.com/easymodeling/easy-modeling/issues/new?template=feature_request.md

## License

EasyModeling is Open Source software licensed under the [APACHE LICENSE, VERSION 2.0][license-2.0].

[license-2.0]: https://www.apache.org/licenses/LICENSE-2.0.html
