package net.skeleton.app;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class PyramidBoundaryTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("net.skeleton");

    @Test
    void webLayerUsesOnlyPublishedServiceContract() {
        noClasses().that().resideInAPackage("..ws.rest..")
                .should().dependOnClassesThat().resideInAnyPackage("..service.impl..", "..dal.api..", "..dal.jpa..")
                .check(classes);
    }

    @Test
    void serviceImplementationDoesNotKnowStorageImplementation() {
        noClasses().that().resideInAPackage("..service.impl..")
                .should().dependOnClassesThat().resideInAPackage("..dal.jpa..")
                .check(classes);
    }

    @Test
    void sharedContractsDoNotDependOnImplementations() {
        noClasses().that().resideInAnyPackage("..infra..", "..service.api..", "..dal.api..")
                .should().dependOnClassesThat().resideInAnyPackage("..service.impl..", "..dal.jpa..", "..ws.rest..")
                .check(classes);
    }
}
