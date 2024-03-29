# Developer's Guide

## Local Environment Setup

### JDK

JDK 1.8 or above

### Test

```shell
./gradlew clean build
```

## Publish to Maven Central Repository

### Publish from pipeline

GitHub [workflow][publish-workflow] `publish` will automatically publish artifacts
to [Maven Central Repository][maven-central-repository] for both snapshot deployment and release deployment.
It will be triggered by git tag pushes.

[publish-workflow]: https://github.com/easymodeling/easy-modeling/actions/workflows/publish.yml

[maven-central-repository]: https://central.sonatype.dev/search?q=easymodeling

#### Publish to Snapshot

Tags end in `-SNAPSHOT` will trigger workflow to perform a snapshot deployment.
For example:

- `0.1.2-SNAPSHOT`
- `0.1.3-SNAPSHOT`

Snapshot versions will not be synchronized to the Central Repository.
Successfully deployed snapshot versions will be found in [snapshot repository][maven snapshot repo].

#### Publish to Release

Tags without `-SNAPSHOT` will trigger workflow to perform a release deployment.
For example:

- `0.1.0-Alpha1`
- `0.1.3`

Successfully deployed snapshot versions will be found in [release repository][maven release repo].

[maven snapshot repo]: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/easymodeling/

[maven release repo]: https://s01.oss.sonatype.org/content/repositories/releases/io/github/easymodeling/

#### Verify deployed artifacts

To verify deployed artifact, run

```shell
./gradlew clean -Partifact-verification :artifact-verification:build
```

The verified artifact version will be printed

```text
> Configure project :artifact-verification
===========================================
artifact-verification.build.gradle

Test on artifact Version: 0.1.4-SNAPSHOT
===========================================
```

### publish from local (Not Recommended)

```shell
export OSSRH_USERNAME=[username]
export OSSRH_PASSWORD=[password]
[ gradle publish commands ]
```
