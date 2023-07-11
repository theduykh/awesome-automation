package scripts;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.theduykh.ata.test.AtaTestNgClassRunner;

public class Test001 extends AtaTestNgClassRunner {

    @Test
    public void test001() {
        I.navigate("https://www.google.com/");
        I.see("Google");
        I.see(By.cssSelector("input[name='q']")).display();

        I.session("new").navigate("https://www.google.com/");
        I.session("new").see("Google");
        I.session("new").see(By.cssSelector("input[name='q']")).display();
    }
}
