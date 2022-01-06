# EasyModeling

[![TEST][test-badge]][test-link]
[![Quality Gate Status][sonar-badge]][sonar-link]
[![Coverage][coverage-badge]][coverage-link]

EasyModeling is a Java library that generates randomly populated models for test use.

[test-badge]: https://github.com/zhezhangz/easy-modeling/actions/workflows/test.yml/badge.svg?branch=master

[test-link]: https://github.com/zhezhangz/easy-modeling/actions/workflows/test.yml

[sonar-badge]: https://sonarcloud.io/api/project_badges/measure?project=zhezhangz_easy-modeling&metric=alert_status

[sonar-link]: https://sonarcloud.io/summary/new_code?id=zhezhangz_easy-modeling

[coverage-badge]: https://sonarcloud.io/api/project_badges/measure?project=zhezhangz_easy-modeling&metric=coverage

[coverage-link]: https://sonarcloud.io/summary/overall?id=zhezhangz_easy-modeling

## What is EasyModeling?

## Supported Field Types

All types listed below, as well as arrays of them and their combinations via generics type parameters, are supported.

Please feel free to raise an issue if you have a type you'd like to see added.

### Basic Types

- Primitive types `boolean`, `byte`, `char`, `double`, `float`, `int`, `long`, `short`
- Boxed Primitive types `Boolean`, `Byte`, `Character`, `Double`, `Float`, `Integer`, `Long`, `Short`
- Strings `java.lang.String`, `java.lang.StringBuilder`

### Arrays

Arrays of any primitive types and reference types listed here are supported, including multidimensional arrays,
regardless of the number of dimensions. However, primitive arrays as type parameters are [not supported](#Unsupported-Primitive-Array-As-Type-Parameters).

### Collections

- Collections `java.util.List`

### Commonly used Types

- Date Time `java.time.Instant`

### Generic Utils

- Optional `java.util.Optional`

### Unsupported Primitive Array As Type Parameters

Primitive arrays as type parameters of any generic types will not be supported, which means any field typed as any
generic type that holding primitive arrays, like `List<int[]>`, `Optional<double[]>`
or `CompletableFuture<boolean[][]>`, will not be recognized so that it will always be set to `null` value. However,
their corresponding boxed reference arrays as type parameters, like `List<Integer[]>`, `Optional<Double[]>`
and `CompletableFuture<Boolean[][]>`, are, of course, well-supported.
