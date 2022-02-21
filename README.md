# EasyModeling

[![TEST][test-badge]][test-link]
[![Quality Gate Status][sonar-badge]][sonar-link]
[![Coverage][coverage-badge]][coverage-link]

A Java annotation processor that generates randomly populated objects for test use.

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

## What does EasyModeling provide?

EasyModeling provides a set of API to help you generate objects for your test, including:

- An annotation ```@Model``` to tell EasyModeling for which classes you would like to generate objects.
- An annotation ```@Field``` and a lot of annotation attributes, which is however totally optional, to customize the
  generated objects.
- A ```next()``` method for each class that returns randomly populated objects.
- A ```builder()``` method for each class that returns randomly populated builders of the objects.

### Simple Example

For example, if you have a class ```Employee``` under test so that you want to generate objects for it, you can firstly
have a modeler configuration like this:

```java
@Model(Employee.class)
public class Modelers {
}
```

Then, EasyModeling will create a modeler named ```EmployeeModeler``` for you, so that you can use the
static ```next()``` method to generate objects:

[//]: # (@formatter:off)
```java
Employee employee = EmployeeModeler.next();
```
[//]: # (@formatter:on)

For specific test scenario, let's say for a test caring employee's age and marital status, by using
static ```builder()``` method, you have the chance to customize the generated objects:

[//]: # (@formatter:off)
```java
Employee employee = EmployeeModeler.builder().age(30).maritalStatus(SINGLE).build();
```
[//]: # (@formatter:on)

## Using EasyModeling

### Maven

-- TBD --

### Gradle

-- TBD --

## Requirements

Java 1.8 or later is required.

## Supported Field Types

All types listed below, as well as arrays of them and their combinations via generics type parameters, are supported.

Please feel free to raise an issue if you have a type you'd like to see added.

### Basic Types

- Primitive types `boolean`, `byte`, `char`, `double`, `float`, `int`, `long`, `short`
- Boxed Primitive types `Boolean`, `Byte`, `Character`, `Double`, `Float`, `Integer`, `Long`, `Short`
- Strings `java.lang.String`, `java.lang.StringBuilder`

### Date Time Types

- Java 8 Date Time types `java.time.Instant` `java.time.LocalDate` `java.time.LocalTime`
  `java.time.LocalDateTime` `java.time.ZonedDateTime`

- Legacy Date Time types `java.util.Date` `java.sql.Date` `java.sql.Timestamp`

### Generic Utils

- Optional `java.util.Optional`

### Collections

- Lists `java.util.List` `java.util.ArrayList` `java.util.LinkedList`
- Sets  `java.util.Set` `java.util.HashSet` `java.util.TreeSet`
- Maps  `java.util.Map` `java.util.HastMap` `java.util.TreeMap`

### Streams

Stream and primitive streams `java.util.stream.Stream` `java.util.stream.IntStream`
`java.util.stream.LongStream` `java.util.stream.DoubleStream`

### Arrays

Arrays of any primitive types and reference types listed here are supported, including multidimensional arrays,
regardless of the number of dimensions. However, primitive arrays as type parameters are not supported.

#### Primitive Array As Type Parameters Are Not Supported

Primitive arrays as type parameters of any generic types will not be supported, which means any field typed as any
generic type that holding primitive arrays, like `List<int[]>`, `Optional<double[]>`
or `CompletableFuture<boolean[][]>`, will not be recognized so that it will always be set to `null` value. However,
their corresponding boxed reference arrays as type parameters, like `List<Integer[]>`, `Optional<Double[]>`
and `CompletableFuture<Boolean[][]>`, are, of course, well-supported.
