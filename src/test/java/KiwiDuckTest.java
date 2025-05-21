import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KiwiDuckTest extends BaseTest {

    @Test
    public void testKiwiDuckSite() {

        driver.get("https://kiwiduck.github.io");

        //переходим к странице Select
        driver.findElement(By.linkText("Selenium practice (elements)")).click();
        driver.findElement(By.xpath("//a[@href='select']")).click();

        //На странице “Select” выбрать одно значение в выпадающем списке
        WebElement heroElement = driver.findElement(By.name("hero"));
        Select heroSelect = new Select(heroElement);
        heroSelect.selectByVisibleText("Linus Torvalds");

        //и несколько в списке, поддерживающем множественный выбор
        WebElement languagesElement = driver.findElement(By.name("languages"));
        Select languagesSelect = new Select(languagesElement);
        if (languagesSelect.isMultiple()) {
            languagesSelect.selectByValue("Java");
            languagesSelect.selectByValue("C#");
        }

        //Нажать на кнопку “GET RESULT”
        driver.findElement(By.xpath("//button[@id='go' and text()='Get Results']")).click();

        //Проверить, что на странице отобразились выбранные значения
        Assert.assertEquals(driver.findElements(By.xpath("//label[@name='result']")).get(0)
                .getText(), heroSelect.getFirstSelectedOption().getText());
        List<WebElement> elements = languagesSelect.getAllSelectedOptions();
        StringBuilder factString = new StringBuilder();
        for (WebElement we : elements) {
            factString.append(we.getDomAttribute("value")).append(", ");
        }
        String expectedString = driver.findElements(By.xpath("//label[@name='result']")).get(1)
                .getText();
        Assert.assertEquals(factString.toString().trim().substring(0, factString.toString().trim().length() - 1),
                expectedString.trim());

        //и ссылка с текстом 'Great! Return to menu' и нажать на нее
        driver.findElement(By.xpath("//a[@href and text()='Great! Return to menu']")).click();

        //переходим к странице Form
        driver.findElement(By.linkText("Selenium practice (elements)")).click();
        driver.findElement(By.xpath("//a[@href='form']")).click();

        //На странице 'Form' заполнить все обязательные поля
        driver.findElement(By.xpath("//label[text()='First Name:']/following-sibling::input"))
                .sendKeys("Александр");
        driver.findElement(By.xpath("//label[text()='Last Name:']/following-sibling::input"))
                .sendKeys("Бухаров");
        driver.findElement(By.xpath("//label[text()='Email:']/following-sibling::input"))
                .sendKeys("AleksandrBukharov@mail.com");
        String sex = "Male";
        driver.findElement(By.xpath("//label[text()='Sex:']/parent::div//text()[contains(.," +
                        Quotes.escape(sex) + ")]/preceding-sibling::input"))
                .click();
        driver.findElement(By.xpath("//label[text()='Address:']/following-sibling::input"))
                .sendKeys("Москва");
        driver.findElement(By.xpath("//label[text()='Avatar:']/following-sibling::input"))
                .sendKeys(System.getProperty("user.dir") + "/src/test/resources/avatar.tif");
        driver.findElement(By.xpath("//label[text()='Tell me something about yourself']/following-sibling::textarea"))
                .sendKeys("Центральный Банк");

        //и нажать на кнопку «ОТПРАВИТЬ»
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        //Проверить, что появилась ссылка с текстом 'Great! Return to menu'
        Assert.assertTrue(driver.findElements(By.xpath("//a[@href and text()='Great! Return to menu']")).size() > 0,
                "ссылка с текстом 'Great! Return to menu' не отображается");

        //и нажать на нее
        driver.findElement(By.xpath("//a[@href and text()='Great! Return to menu']")).click();

        //переходим к странице IFrame
        driver.findElement(By.linkText("Selenium practice (elements)")).click();
        driver.findElement(By.xpath("//a[@href='iframe']")).click();

        //На странице “IFrame” ввести код, выведенный на этой странице, в поле ввода
        driver.switchTo().frame("code-frame");
        String code = driver.findElement(By.xpath("//label[@id='code']")).getText().substring("Your code is: ".length());
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//input[@name='code']")).sendKeys(code);
        //и нажать на кнопку «VERIFY»
        driver.findElement(By.xpath("//input[@value='Verify']")).click();
        //Проверить, что появилась ссылка с текстом “Great! Return to menu”и нажать на неё
        Assert.assertTrue(driver.findElements(By.xpath("//a[@href and text()='Great! Return to menu']")).size() > 0,
                "ссылка с текстом 'Great! Return to menu' не отображается");
        driver.findElement(By.xpath("//a[@href and text()='Great! Return to menu']")).click();
    }
}
