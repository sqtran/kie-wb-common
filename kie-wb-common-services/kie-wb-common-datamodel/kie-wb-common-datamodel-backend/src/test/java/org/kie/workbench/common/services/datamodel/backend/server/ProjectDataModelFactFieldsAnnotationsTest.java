/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.workbench.common.services.datamodel.backend.server;

import java.util.Map;
import java.util.Set;

import org.drools.workbench.models.commons.backend.oracle.ProjectDataModelOracleImpl;
import org.drools.workbench.models.datamodel.oracle.Annotation;
import org.drools.workbench.models.datamodel.oracle.TypeSource;
import org.junit.Test;
import org.kie.workbench.common.services.datamodel.backend.server.builder.projects.ClassFactBuilder;
import org.kie.workbench.common.services.datamodel.backend.server.builder.projects.ProjectDataModelOracleBuilder;
import org.kie.workbench.common.services.datamodel.backend.server.testclasses.Product;
import org.kie.workbench.common.services.datamodel.backend.server.testclasses.annotations.SmurfHouse;

import static org.junit.Assert.*;
import static org.kie.workbench.common.services.datamodel.backend.server.ProjectDataModelOracleTestUtils.*;

/**
 * Tests for Fact's annotations
 */
public class ProjectDataModelFactFieldsAnnotationsTest {

    @Test
    public void testProjectDMOZeroAnnotationAttributes() throws Exception {
        final ProjectDataModelOracleBuilder builder = ProjectDataModelOracleBuilder.newProjectOracleBuilder();
        final ProjectDataModelOracleImpl oracle = new ProjectDataModelOracleImpl();

        final ClassFactBuilder cb = new ClassFactBuilder( builder,
                                                          Product.class,
                                                          false,
                                                          TypeSource.JAVA_PROJECT );
        cb.build( oracle );

        assertEquals( 1,
                      oracle.getProjectModelFields().size() );
        assertContains( "org.kie.workbench.common.services.datamodel.backend.server.testclasses.Product",
                        oracle.getProjectModelFields().keySet() );

        final Map<String, Set<Annotation>> fieldAnnotations = oracle.getProjectTypeFieldsAnnotations().get( "org.kie.workbench.common.services.datamodel.backend.server.testclasses.Product" );
        assertNotNull( fieldAnnotations );
        assertEquals( 0,
                      fieldAnnotations.size() );
    }

    @Test
    public void testProjectDMOAnnotationAttributes() throws Exception {
        final ProjectDataModelOracleBuilder builder = ProjectDataModelOracleBuilder.newProjectOracleBuilder();
        final ProjectDataModelOracleImpl oracle = new ProjectDataModelOracleImpl();

        final ClassFactBuilder cb = new ClassFactBuilder( builder,
                                                          SmurfHouse.class,
                                                          false,
                                                          TypeSource.JAVA_PROJECT );
        cb.build( oracle );

        assertEquals( 1,
                      oracle.getProjectModelFields().size() );
        assertContains( "org.kie.workbench.common.services.datamodel.backend.server.testclasses.annotations.SmurfHouse",
                        oracle.getProjectModelFields().keySet() );

        final Map<String, Set<Annotation>> fieldsAnnotations = oracle.getProjectTypeFieldsAnnotations().get( "org.kie.workbench.common.services.datamodel.backend.server.testclasses.annotations.SmurfHouse" );
        assertNotNull( fieldsAnnotations );
        assertEquals( 2,
                      fieldsAnnotations.size() );

        assertTrue( fieldsAnnotations.containsKey( "occupant" ) );
        final Set<Annotation> occupantAnnotations = fieldsAnnotations.get( "occupant" );
        assertNotNull( occupantAnnotations );
        assertEquals( 1,
                      occupantAnnotations.size() );

        final Annotation annotation = occupantAnnotations.iterator().next();
        assertEquals( "org.kie.workbench.common.services.datamodel.backend.server.testclasses.annotations.SmurfFieldDescriptor",
                      annotation.getQualifiedTypeName() );
        assertEquals( "blue",
                      annotation.getParameters().get( "colour" ) );
        assertEquals( "M",
                      annotation.getParameters().get( "gender" ) );
        assertEquals( "Brains",
                      annotation.getParameters().get( "description" ) );

        assertTrue( fieldsAnnotations.containsKey( "positionedOccupant" ) );
        final Set<Annotation> posOccupantAnnotations = fieldsAnnotations.get( "positionedOccupant" );
        assertNotNull( posOccupantAnnotations );
        assertEquals( 1,
                      posOccupantAnnotations.size() );

        final Annotation annotation2 = posOccupantAnnotations.iterator().next();
        assertEquals( "org.kie.workbench.common.services.datamodel.backend.server.testclasses.annotations.SmurfFieldPositionDescriptor",
                      annotation2.getQualifiedTypeName() );
        assertEquals( 1,
                      annotation2.getParameters().get( "value" ) );
    }

}
