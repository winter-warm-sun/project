package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({BlogLoginTest.class,BlogListTest.class,BlogDetailTest.class,BlogEditTest.class,DriverQuitTest.class})
public class RunSuite {
}
