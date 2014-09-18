package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.libraries.URL;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import be.normegil.librarium.tool.test.model.data.AbstractDataEqualityTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UTRESTErrorEquality extends AbstractDataEqualityTest<RESTError> {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<RESTError> FACTORY = FactoryRepository.get(RESTError.class);
	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<URL> URL_FACTORY = FactoryRepository.get(URL.class);

	@Override
	protected RESTError getNewEntity() {
		return FACTORY.getNew();
	}

	@Test
	public void testUnchanged() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = new RESTError(entity);
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentCode() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setCode(entity.getCode() + 1)
				.build();
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentStatus() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setHttpStatus(entity.getStatus() + 1)
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentMessage() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setMessage(entity.getMessage() + 1)
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentDeveloperMessage() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setDeveloperMessage(entity.getDeveloperMessage() + 1)
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentMoreInfoURL() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setMoreInfoURL(URL_FACTORY.getNext())
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentThrowable() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setThrowable(new IllegalArgumentException())
				.build();
		assertNotEquals(entity, copy);
	}
}