package io.github.easymodeling.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.easymodeling.Field;
import io.github.easymodeling.Model;
import io.github.easymodeling.Models;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "io.github.easymodeling.modeler")
public class ModelerPackageArchTest {

    @ArchTest
    private final ArchRule modeler_pkg_should_not_depend_on_Model_and_Models_annotations =
            noClasses()
                    .that().resideInAPackage("io.github.easymodeling.modeler..")
                    .should().dependOnClassesThat().belongToAnyOf(Model.class, Models.class);

    @ArchTest
    private final ArchRule modeler_pkg_should_not_depend_on_Field_annotations =
            noClasses()
                    .that().resideInAPackage("io.github.easymodeling.modeler..")
                    .should().dependOnClassesThat().belongToAnyOf(Field.class);

    @ArchTest
    private final ArchRule should_modeler_pkg_not_depends_on_processor_pkg =
            noClasses()
                    .that().resideInAPackage("io.github.easymodeling.modeler..")
                    .should().dependOnClassesThat().resideInAnyPackage("io.github.easymodeling.processor..");
}
