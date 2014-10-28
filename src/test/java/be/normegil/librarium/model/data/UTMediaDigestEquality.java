package be.normegil.librarium.model.data;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.libraries.ClassWrapper;
import be.normegil.librarium.libraries.URL;
import be.normegil.librarium.model.dao.DatabaseDAO;
import be.normegil.librarium.model.data.game.Game;
import be.normegil.librarium.model.data.people.StaffMember;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.EntityHelper;
import be.normegil.librarium.tool.FactoryRepository;
import be.normegil.librarium.tool.test.model.data.AbstractDataEqualityTest;
import org.junit.Test;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UTMediaDigestEquality extends AbstractDataEqualityTest<Media.MediaDigest> {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<ReleaseDate> RELEASE_DATE_FACTORY = FactoryRepository.get(ReleaseDate.class);
	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<Universe> UNIVERSE_FACTORY = FactoryRepository.get(Universe.class);
	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<Game> MEDIA_FACTORY = FactoryRepository.get(Game.class);
	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<URL> URL_FACTORY = FactoryRepository.get(URL.class);
	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<StaffMember> STAFF_MEMBER_FACTORY = FactoryRepository.get(StaffMember.class);

	@Override
	protected Media.MediaDigest getNewEntity() {
		return new Media.MediaDigest();
	}

	@Test
	public void testUnchanged() throws Exception {
		Media entity = getMedia();
		URL url = URL_FACTORY.getNew();
		URI baseURI = url.toURI();
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.fromBase(baseURI, entity);
		digest2.fromBase(baseURI, entity);
		assertEquals(digest1, digest2);
	}

	@Test
	public void testDifferentUniverses() throws Exception {
		Media entity = getMedia();
		URL url = URL_FACTORY.getNew();
		URI baseURI = url.toURI();
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.fromBase(baseURI, entity);
		Universe universe = UNIVERSE_FACTORY.getNew();
		new EntityHelper().setId(universe, UUID.randomUUID());
		entity.addUniverse(universe);
		digest2.fromBase(baseURI, entity);
		assertNotEquals(digest1, digest2);
	}

	@Test
	public void testDifferentUniverseDAO() throws Exception {
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.setUniverseDAO(new DatabaseDAO<>(Universe.class));
		digest2.setUniverseDAO(new DatabaseDAO<>(Universe.class));
		assertEquals(digest1, digest2);
	}

	@Test
	public void testDifferentReleaseDates() throws Exception {
		Media entity = getMedia();
		URL url = URL_FACTORY.getNew();
		URI baseURI = url.toURI();
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.fromBase(baseURI, entity);
		ReleaseDate releaseDate = RELEASE_DATE_FACTORY.getNew(false, true);
		entity.releaseDates.add(releaseDate);
		digest2.fromBase(baseURI, entity);
		assertNotEquals(digest1, digest2);
	}

	@Test
	public void testDifferentReleaseDateDAO() throws Exception {
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.setReleaseDateDAO(new DatabaseDAO<>(ReleaseDate.class));
		digest2.setReleaseDateDAO(new DatabaseDAO<>(ReleaseDate.class));
		assertEquals(digest1, digest2);
	}

	@Test
	public void testDifferentStaffMembers() throws Exception {
		Media entity = getMedia();
		URL url = URL_FACTORY.getNew();
		URI baseURI = url.toURI();
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.fromBase(baseURI, entity);
		StaffMember staffMember = STAFF_MEMBER_FACTORY.getNew(true, true);
		ClassWrapper<Media> mediaClassWrapper = new ClassWrapper<>(Media.class);
		Collection<StaffMember> staffMembers = (Collection<StaffMember>) mediaClassWrapper.getField("staffMembers").get(entity);
		staffMembers.add(staffMember);
		digest2.fromBase(baseURI, entity);
		assertNotEquals(digest1, digest2);
	}

	@Test
	public void testDifferentStaffMembersDAO() throws Exception {
		Media.MediaDigest digest1 = new Media.MediaDigest();
		Media.MediaDigest digest2 = new Media.MediaDigest();
		digest1.setStaffMembersDAO(new DatabaseDAO<>(StaffMember.class));
		digest2.setStaffMembersDAO(new DatabaseDAO<>(StaffMember.class));
		assertEquals(digest1, digest2);
	}

	private Media getMedia() {
		return MEDIA_FACTORY.getNew(true, true);
	}
}