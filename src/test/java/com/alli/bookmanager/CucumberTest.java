package com.alli.bookmanager;

import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.ConfigurationParameter;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectClasspathResource("com/alli/bookmanager") // points to feature files
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.alli.bookmanager")
public class CucumberTest {
}
