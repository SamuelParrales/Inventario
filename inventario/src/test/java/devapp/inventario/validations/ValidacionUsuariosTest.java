package devapp.inventario.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidacionUsuariosTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testValidarUsuarios() {
		ValidacionUsuarios val = new ValidacionUsuarios();
		boolean result = val.ValidarCorreo("luisdominguez818@gmail.com");
		assertEquals(true,result);
	}

	@Test
	void testValidarCI() {
		System.out.println("Validar CI");
		ValidacionUsuarios val = new ValidacionUsuarios();
		boolean result = val.ValidarCI("1315478162");
		assertEquals(true,result);
	}

	@Test
	void testValidarPassword() {
		System.out.println("Validar Password");
		ValidacionUsuarios val = new ValidacionUsuarios();
		boolean result = val.ValidarPassword("hoLo(%&a*/7899o");
		assertEquals(true,result);
		//La contraseña debe tener al entre 8 y 16 caracteres
		//, al menos un dígito, al menos una minúscula, al menos una mayúscula y 
		//al menos un caracter no alfanumérico.
	}

	@Test
	void testValidarTelefono() {
		System.out.println("Validar Telefono");
		ValidacionUsuarios val = new ValidacionUsuarios();
		boolean result = val.ValidarTelefono("087407849");
		assertEquals(false,result);
	}

}
