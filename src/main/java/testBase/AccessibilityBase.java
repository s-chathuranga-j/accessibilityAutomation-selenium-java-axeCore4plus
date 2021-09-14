package testBase;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccessibilityBase extends FrameworkBase
{
    private static List<String> tags = Arrays.asList("wcag2a", "wcag2aa", "wcag21aa");
    private static String reportPath = System.getProperty("user.dir") + "\\src\\test\\java\\reports\\";

    public static void checkAccessibilityViolations() throws IOException
    {
        String reportFile = reportPath + "accessibilityReport";
        WebDriver webDriver = webDriver();
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);
        Results results = builder.analyze(webDriver);
        saveReport(results, reportFile);
    }

    public static void checkAccessibilityViolationsOfSelector(String selector) throws FileNotFoundException
    {
        String reportFile = reportPath + "accessibilityReport";
        WebDriver webDriver = webDriver();
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);
        Results results = builder.include(Collections.singletonList(selector)).analyze(webDriver);
        saveReport(results, reportFile);
        System.out.println("A11y test report saved to: " + reportPath);
    }

    public static void saveReport(Results results, String reportFile) throws FileNotFoundException {
        List<Rule> violations = results.getViolations();
        if (violations.size() == 0)
        {
            Assert.assertTrue(true, "No violations found");
        }
        else
        {
            JsonParser jsonParser = new JsonParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            AxeReporter.writeResultsToJsonFile(reportFile, results);
            JsonElement jsonElement = jsonParser.parse(new FileReader(reportFile + ".json"));
            String prettyJson = gson.toJson(jsonElement);
            AxeReporter.writeResultsToTextFile(reportFile, prettyJson);
            Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");
        }
    }
}
