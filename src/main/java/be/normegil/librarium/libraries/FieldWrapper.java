package be.normegil.librarium.libraries;

import be.normegil.librarium.util.exception.IllegalAccessRuntimeException;

import java.lang.reflect.Field;

public class FieldWrapper {

	private Field field;

	public FieldWrapper(Field field) {
		this.field = field;
	}

	public String getName() {
		return field.getName();
	}

	public void set(Object entity, Object value) {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		try {
			field.set(entity, value);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessRuntimeException(e);
		} finally {
			field.setAccessible(accessible);
		}
	}

	public Field getOriginalField() {
		return field;
	}
}
