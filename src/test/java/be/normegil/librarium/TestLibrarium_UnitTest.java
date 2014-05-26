package be.normegil.librarium;

import be.normegil.librarium.model.ModelTestSuite;
import be.normegil.librarium.rest.RESTTestSuite;
import be.normegil.librarium.util.UtilTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilTestSuite.class,
        ModelTestSuite.class,
        RESTTestSuite.class
})
public class TestLibrarium_UnitTest {

}