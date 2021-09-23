package org.apache.harmony.tests.java.io;

import java.io.ObjectStreamClass;


public class ComputeSerialVersionUIDTest extends junit.framework.TestCase {

    public void testComputeSUIDClass() throws Exception {
        assertEquals(-5877374382732244721L,
                computeSerialVersionUID(SerializationTestClass.TestClassName1.class));
        assertEquals(-2258784348609133821L,
                computeSerialVersionUID(SerializationTestClass.TestClassName2T_T$T.class));
        assertEquals(-5674447587118957354L,
                computeSerialVersionUID(SerializationTestClass.TestClassModifierPublic.class));
        assertEquals(8333249076871004334L,
                computeSerialVersionUID(SerializationTestClass.TestClassModifierAbstract.class));
        assertEquals(-6752991881983868187L,
                computeSerialVersionUID(SerializationTestClass.TestClassModifierFinal.class));
        assertEquals(-2046603329186110997L,
                computeSerialVersionUID(SerializationTestClass.TestClassModifierInterface.class));
    }

    public void testComputeSUIDInterfaces() throws Exception {
        assertEquals(2385879270919801624L, computeSerialVersionUID(SerializationTestClass.TestInterfaces.class));
        assertEquals(-3876044724689092051L, computeSerialVersionUID(SerializationTestClass.TestInterfacesA.class));
        assertEquals(6691168002125833763L, computeSerialVersionUID(SerializationTestClass.TestInterfacesAB.class));
        assertEquals(-3862602835688739317L, computeSerialVersionUID(SerializationTestClass.TestInterfacesBA.class));
        assertEquals(6153219913626150137L, computeSerialVersionUID(SerializationTestClass.TestInterfacesC.class));
        assertEquals(-5230940296111061949L, computeSerialVersionUID(SerializationTestClass.TestInterfacesCA.class));
        assertEquals(-561891731488612449L, computeSerialVersionUID(SerializationTestClass.TestInterfacesABC.class));
        assertEquals(7173098887933679885L, computeSerialVersionUID(SerializationTestClass.TestInterfacesACB.class));
        assertEquals(7417451177210251082L, computeSerialVersionUID(SerializationTestClass.TestInterfacesBAC.class));
        assertEquals(6457265192863049241L, computeSerialVersionUID(SerializationTestClass.TestInterfacesBCA.class));
        assertEquals( 5890948387530452778L, computeSerialVersionUID(SerializationTestClass.TestInterfacesCAB.class));
        assertEquals(-7493935950381842313L, computeSerialVersionUID(SerializationTestClass.TestInterfacesCBA.class));
    }


    public void testComputeSUIDFields() throws Exception {
        assertEquals(-30967666739349603L, computeSerialVersionUID(SerializationTestClass.TestFieldsNone.class));
        assertEquals(8551211022820107208L, computeSerialVersionUID(SerializationTestClass.TestFieldsOneFinal.class));
        assertEquals(-7774226929120968860L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoFinal.class));
        assertEquals(-8196468848051541845L, computeSerialVersionUID(SerializationTestClass.TestFieldsOnePrivate.class));
        assertEquals(-7861029019096564216L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoPrivate.class));
        assertEquals(81248916710250820L, computeSerialVersionUID(SerializationTestClass.TestFieldsOneProtected.class));
        assertEquals(280835377416490750L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoProtected.class));
        assertEquals(-2290036437752730858L, computeSerialVersionUID(SerializationTestClass.TestFieldsOnePublic.class));
        assertEquals(-6124932240571007214L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoPublic.class));
        assertEquals(6101579853402497691L, computeSerialVersionUID(SerializationTestClass.TestFieldsOneStatic.class));
        assertEquals(-7900976994549865116L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoStatic.class));
        assertEquals(-4499355017417065560L, computeSerialVersionUID(SerializationTestClass.TestFieldsOneTransient.class));
        assertEquals(3747907454018261619L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoTransient.class));
        assertEquals(-4945042592592621725L, computeSerialVersionUID(SerializationTestClass.TestFieldsOneVolatile.class));
        assertEquals(8983117060325881490L, computeSerialVersionUID(SerializationTestClass.TestFieldsTwoVolatile.class));
        assertEquals(-8336483965186710722L, computeSerialVersionUID(SerializationTestClass.TestFieldSignatures.class));
    }


    public void testComputeSUIDConstructors() throws Exception {
        assertEquals(-614706174292151857L, computeSerialVersionUID(SerializationTestClass.TestConstructorNone.class));
        assertEquals(-3706135726712902027L, computeSerialVersionUID(SerializationTestClass.TestConstructorOne.class));
        assertEquals(-8094991171016233719L, computeSerialVersionUID(SerializationTestClass.TestConstructorPrivate.class));
        assertEquals(-8117933510362198687L, computeSerialVersionUID(SerializationTestClass.TestConstructorProtected.class));
        assertEquals(9205589590060392077L, computeSerialVersionUID(SerializationTestClass.TestConstructorPublic.class));
        assertEquals(5408111072458161992L, computeSerialVersionUID(SerializationTestClass.TestConstructorSignature.class));
        assertEquals(625104530709630511L, computeSerialVersionUID(SerializationTestClass.TestConstructorTwo.class));
        assertEquals(3737423569701135020L, computeSerialVersionUID(SerializationTestClass.TestConstructorTwoReverse.class));
    }

    public void testComputeSUIDMethods() throws Exception {
        assertEquals(8872679581767836990L, computeSerialVersionUID(SerializationTestClass.TestMethodPrivate.class));
        assertEquals(-4558121473827608582L, computeSerialVersionUID(SerializationTestClass.TestMethodAbstract.class));
        assertEquals(4148772500508720405L, computeSerialVersionUID(SerializationTestClass.TestMethodFinal.class));
        assertEquals(6329381817306256121L, computeSerialVersionUID(SerializationTestClass.TestMethodNative.class));
        assertEquals(-2701115429311553102L, computeSerialVersionUID(SerializationTestClass.TestMethodProtected.class));
        assertEquals(-4092306049997161465L, computeSerialVersionUID(SerializationTestClass.TestMethodPublic.class));
        assertEquals(-7948580256486289776L, computeSerialVersionUID(SerializationTestClass.TestMethodStatic.class));
        assertEquals(4085068229405300186L, computeSerialVersionUID(SerializationTestClass.TestMethodSignature.class));
        assertEquals(-5743322978294773864L, computeSerialVersionUID(SerializationTestClass.TestMethodReturnSignature.class));
        assertEquals(-6908429504335657476L, computeSerialVersionUID(SerializationTestClass.TestMethodSynchronized.class));
    }

    private static long computeSerialVersionUID(Class<?> clazz) {
        return ObjectStreamClass.lookup(clazz).getSerialVersionUID();
    }
}
