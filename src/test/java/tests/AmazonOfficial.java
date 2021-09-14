package tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.testng.annotations.Test;
import testBase.AccessibilityBase;
import testBase.FrameworkBase;

import java.io.IOException;

public class AmazonOfficial extends FrameworkBase
{
    AccessibilityBase a11y;

    @Test
    public void testAmazon() throws IOException {
        webDriver().get("https://www.amazon.com");
        a11y.checkAccessibilityViolations();
    }
}
