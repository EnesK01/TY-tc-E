import com.microsoft.playwright.*;
import com.microsoft.playwright.impl.junit.PlaywrightExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.awt.*;
import java.beans.Transient;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Ty {
    private static final Logger logger = LoggerFactory.getLogger(Ty.class);

    @Test
    public void Sepeteekle() throws InterruptedException {
/*Login olmayan misafir user ile site üzerindeki rastgele bir kategori ve o kategorideki rastgele bir ürüne tıklandığında ekranın boş gelmemesi
Prod ortamı
 */
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://www.trendyol.com/");
        Thread.sleep(2000);
        page.setViewportSize(width, height);
        assertThat(page).hasTitle(Pattern.compile("Trendyol"));
        page.locator("//button[@id='onetrust-accept-btn-handler']").click();
        Thread.sleep(2000);
        page.locator("//*[contains(text(),'TÜM KATEGORİLER')]").hover();
        Random random = new Random();
        int productcategory = random.nextInt(6)+1;
        page.locator("(//div[@class='left-side-container'])" + "[" + productcategory + "]").hover();
        //ürünün üstüne geldiğimizde sol tarafta seçilen olanın özelliği aktif olmaktadır.
        String category = page.locator("(//div[@class='left-side-container active'])").textContent();
        logger.info(category + " kategorisine gidildi");
        int productchoice = random.nextInt(100) + 1;
        String a = page.locator("(//div[@class='category-item-container']/ul/li/a)" + "[" + productchoice + "]").getAttribute("title");
        logger.info(a + " ürününe tiklanacak");
        page.locator("(//*[contains(text(),'" + a + "')])[1]").click();
        logger.info(a + " ürününe tiklandi");
        page.locator("(//div[@class='image-overlay'])[1]").waitFor();
        page.locator("(//div[@class='image-overlay'])[1]").isEnabled();
        page.waitForLoadState();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("product_list.jpeg")));
        page.waitForLoadState();
        page.close();
        browser.close();
        playwright.close();
    }
}
