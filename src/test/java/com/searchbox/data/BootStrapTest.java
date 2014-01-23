package com.searchbox.data;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:testContext.xml")
//@ActiveProfiles("dev")
public class BootStrapTest {

    //private BootStrap bootStrap = new BootStrap();

    @Test
    public void onApplicationEvent() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void main() {
    	System.out.println("Wasssssup");
        org.junit.Assert.assertTrue(true);
    }
}
