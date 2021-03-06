package be.normegil.librarium.model.dao.game;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.model.data.game.Game;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import be.normegil.librarium.tool.test.model.dao.AbstractDAOTest;

import static org.junit.Assert.assertEquals;

public class UTGameDatabaseDAO extends AbstractDAOTest<GameDatabaseDAO, Game> {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<Game> FACTORY = FactoryRepository.get(Game.class);
	private static final String ALTERNATIVE_TITLE = "AlternativeTitle";

	@Override
	protected GameDatabaseDAO initDAO() {
		return new GameDatabaseDAO();
	}

	@Override
	protected Object getEntityId(final Game game) {
		return game.getId();
	}

	@Override
	protected void changeEntity(final Game game) {
		game.setTitle(ALTERNATIVE_TITLE);
	}

	@Override
	protected void assertChangedPropertyEquals(final Game foundEntity) {
		assertEquals(ALTERNATIVE_TITLE, foundEntity.getTitle());
	}

	@Override
	protected Game getNewData() {
		return FACTORY.getNext();
	}
}
