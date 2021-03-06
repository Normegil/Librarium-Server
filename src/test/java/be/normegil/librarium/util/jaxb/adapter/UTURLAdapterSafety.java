package be.normegil.librarium.util.jaxb.adapter;

import be.normegil.librarium.libraries.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTURLAdapterSafety {

	private static final String EMPTY_STRING = "";
	private URLAdapter entity;

	@Before
	public void setUp() throws Exception {
		entity = new URLAdapter();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testUnmarshal_Null() throws Exception {
		URL url = entity.unmarshal(null);
		assertEquals(null, url);
	}

	@Test
	public void testUnmarshal_Empty() throws Exception {
		URL url = entity.unmarshal(EMPTY_STRING);
		assertEquals(null, url);
	}

	@Test
	public void testMarshal_Null() throws Exception {
		String representation = entity.marshal(null);
		assertEquals(null, representation);
	}
}