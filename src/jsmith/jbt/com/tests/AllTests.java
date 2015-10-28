package jsmith.jbt.com.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCompanyCreate.class, TestCustomerCreate.class })
public class AllTests {

}
