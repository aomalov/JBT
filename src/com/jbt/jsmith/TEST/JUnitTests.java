package com.jbt.jsmith.TEST;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author andrew
 * 
 * All Junit4 tests assambled into a suite
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ TestCompanyCreate.class, TestCustomerCreate.class })
public class JUnitTests {

}
