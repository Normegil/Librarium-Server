package be.normegil.librarium.model.dao.people;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTPersonDatabaseDAOSafety.class,
		UTPersonDatabaseDAO.class
})
public class PersonDatabaseDAOTestSuite {
}