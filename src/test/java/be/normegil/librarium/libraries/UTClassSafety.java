package be.normegil.librarium.libraries;

import be.normegil.librarium.model.data.Entity;
import be.normegil.librarium.tool.validation.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTClassSafety {

	private static final Class<Class> CLASS = new Class<>(Class.class);
	private static final String EMPTY_STRING = "";
	private Class<Entity> entity;

	@Before
	public void setUp() throws Exception {
		entity = new Class<>(Entity.class);
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetMethod_Null() throws Exception {
		java.lang.Class<?>[] classes = {};
		Validator.validate(entity, CLASS.getMethod("getMethod", String.class, classes.getClass()), null, classes);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetMethod_Empty() throws Exception {
		java.lang.Class<?>[] classes = {};
		Validator.validate(entity, CLASS.getMethod("getMethod", String.class, classes.getClass()), EMPTY_STRING, classes);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetField_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getField", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetField_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getField", String.class), EMPTY_STRING);
	}
}