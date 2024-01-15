package org.example.models;

import org.example.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {

    Cuenta c;

    @BeforeEach
    void iniciarMetodoTest(){
        this.c = new Cuenta("Andres", new BigDecimal("123.100023"));
        System.out.println("Inicio");
    }

    @AfterEach
    void tearDown(){
        System.out.println("Finalzando metodos");
    }

    @BeforeAll
    void beforeAll(){
        System.out.println("Inicializando el test");
    }

    @AfterAll
    void afterAll(){
        System.out.println("Finalizando el TEST");
    }

    @Test
    @DisplayName("Probando nombre de la cuenta")
    void test_nombre_cuenta(){

        //c.setPersona("Andres");
        String esperado = "Andres";
        String real = c.getPersona();
        //Assertions pertenece a JUnit jupiter
        assertNotNull(real, ()-> "La cuenta no puede ir vacia");
        Assertions.assertEquals(esperado, real, () -> "El nombre insertado no es igual al esperado");
        assertTrue(real.equals("Andres"), "Nombre de la cuenta espérado");
    }

    @Test
    void testSaldoCuenta(){
        c = new Cuenta("Andres", new BigDecimal("1000.222"));
        assertEquals(1000.222, c.getSaldo().doubleValue());
        assertFalse(c.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaCuenta(){
        Cuenta c = new Cuenta("John Doe", new BigDecimal("1000.2222"));
        Cuenta c2 = new Cuenta("John Doe", new BigDecimal("1000.2222"));

        //assertNotEquals(c, c2);
        //comparando por valoer
        assertEquals(c, c2); //llama la clase equals ya que se sobreescribio en la clase Cuenta
    }

    @Test
    void testDebitoCuenta(){
        //Cuenta c3 = new Cuenta("Andres", new BigDecimal("1000.002"));
        c.debito(new BigDecimal(100));
        assertNotNull(c.getSaldo());
        assertEquals(23, c.getSaldo().intValue());
        assertEquals("23.100023", c.getSaldo().toPlainString());

    }

    @Test
    void testCreditoCuenta(){
        Cuenta c4 = new Cuenta("Andres", new BigDecimal("1000.002"));
        c4.credito(new BigDecimal(100));
        assertNotNull(c4.getSaldo());
        assertEquals(1100, c4.getSaldo().intValue());
        assertEquals("1100.002", c4.getSaldo().toPlainString());

    }

    @Test
    void testDineroInsuficienteExceptionCuenta(){
        Cuenta c = new Cuenta("Luis", new BigDecimal("1000.99"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () ->{
            c.debito(new BigDecimal(1200));
        });

        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuenta() {
        Cuenta c = new Cuenta("Luis", new BigDecimal("2500"));
        Cuenta c2 = new Cuenta("Gatsby", new BigDecimal("1500.99"));

        Banco b = new Banco();
        b.setNombre("BBVA");
        b.transferir(c, c2, new BigDecimal(500));

        assertEquals("2000", c.getSaldo().toPlainString());
        assertEquals("2000.99", c2.getSaldo().toPlainString());

    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta c = new Cuenta("Luis", new BigDecimal("2500"));
        Cuenta c2 = new Cuenta("Gatsby", new BigDecimal("1500.99"));

        Banco b = new Banco();
        b.addCuenta(c);
        b.addCuenta(c2);
        b.setNombre("BBVA");
        b.transferir(c, c2, new BigDecimal(500));

        assertAll(
                () -> {
                    assertEquals("2000", c.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals("2000.99", c2.getSaldo().toPlainString());
                },
                () -> {
                    //test para obtener el numero de cuentas
                    assertEquals(2, b.getCuentas().size());
                },
                () -> {
                    //test para obtener nombre de banco
                    assertEquals("BBVA", c.getBanco().getNombre());
                },
                () -> {
                    //test para obtener nombre desde Banco
                    assertEquals("Luis", b.getCuentas().stream().filter(cF ->cF.getPersona()
                            .equals("Luis")).findFirst().get().getPersona());
                });
    }

    //condicionales
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows(){

    }


    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloLinuxMac(){

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testSoloJDK8(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void testSoloJDK11(){

    }

    @Test
    void imprimirSystemPrperties(){
        Properties p = System.getProperties();
        p.forEach((k,v) -> System.out.println(k + ":" + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = ".*11.*")
    void testJavaVersion(){

    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testSolo64(){

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-11.0.11.*")
    void testJavaHome(){

    }

    @Test
    @EnabledIfSystemProperty(named = "user.name", matches = "danie")
    void testUserName(){

    }

    @Test
    @EnabledIfSystemProperty(named = "ENV2", matches = "de12v")
    void testDev(){

    }

    //condicional
    @Test
    void imprimirVariablesAmbiente(){
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k,v) -> System.out.println(k + ":" + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-11.0.11.*")
    void testJavaHome2(){

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "12")
    void testDev2(){

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
    void testDev3(){

    }

    //condicionale con assumpciones
    @Test
    void testAssumptions(){
        //el assumption es como un if, si la condicion es falsa, no se ejecuta el test
        Assumptions.assumeTrue("DEV".equals(System.getenv("ENVIRONMENT")));
        assertTrue("DEV".equals(System.getenv("ENVIRONMENT")));
    }

    //Clases de test anidadas con @Nested
    @Nested
    class SistemaOperativoTest{
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows(){

        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac(){

        }
    }

    //Repitiendo pruebas con @RepeatTest
    @RepeatedTest(value = 5, name = "{displayName} - Repeticion numero {currentRepetition} de {totalRepetitions}")
    @DisplayName("Prueba Repetida")
    void testRepeticion(){
        assertEquals(5, 5);
    }

    //Escribiendo pruebas parametrizadas con @ParameterizedTest
    @ParameterizedTest(name = "numero {index} ejecutando con valor {0}")
    @ValueSource(ints = {100, 200, 300, 500, 700, 1000})
    void testDebitoCuentaValueSource(int monto) {
        Cuenta c = new Cuenta("Luis", new BigDecimal("1000.12345"));
        BigDecimal montoDecimal = BigDecimal.valueOf(monto);

        c.debito(montoDecimal);

        assertNotNull(c.getSaldo());
        assertEquals(new BigDecimal("1000.12345").subtract(montoDecimal), c.getSaldo());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hola", "Mundo", "JUnit"})
    void testConString(String palabra) {
        Assertions.assertTrue(palabra.length() > 0);
    }

    // Ejemplo con argumentos de tipo int
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20})
    void testConEnteroPositivo(int numero) {
        Assertions.assertTrue(numero > 0);
    }

    //ejemplo con @Tag
    @Test
    @Tag("smoke")
    void testPruebaRapida() {
        Assertions.assertTrue(true);
    }

    @Test
    @Tag("unit")
    void testOtraPrueba() {
        Assertions.assertEquals(4, 2 + 2);
    }

    @Test
    @Tag("integration")
    void testPruebaDeIntegracion() {
        Assertions.assertNotEquals(0, 5 - 3);
    }

    //Inyeccion de dependencias
    @Test
    void testWithTestInfo(TestInfo testInfo) {
        System.out.println("Ejecutando prueba: " + testInfo.getDisplayName());
    }

    @Test
    void testWithTestReporter(TestReporter testReporter) {
        testReporter.publishEntry("clave", "valor");
    }

    @Test
    void testWithBoth(TestInfo testInfo, TestReporter testReporter) {
        System.out.println("Ejecutando prueba: " + testInfo.getDisplayName());
        testReporter.publishEntry("clave", "valor");
    }

    //Timeouts
    @Test
    @Timeout(1)
    void pruebaTimeout() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
    }

    //Test timeout con mensaje
    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void pruebaTimeoutConMensaje() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
    }
}