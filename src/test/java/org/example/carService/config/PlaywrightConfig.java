package org.example.carService.config;

import com.microsoft.playwright.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Optional;

import java.nio.file.Path;

/**
 * This is the 1 class what I should to start with
 */
@Slf4j
@Data
public class PlaywrightConfig {
    private final Playwright playwright;
    private final Browser browser;
    private final String browserName;
    private final Page page;
    private final BrowserContext browserContext;
    private final Boolean traceEnabled;
    private final Boolean headless;
    private final String downloadPath = "target/download"; // todo: MOVE TO TEST CONFIG

    public PlaywrightConfig(@Optional("chrome") String browserName, @Optional("true") Boolean traceEnabled, @Optional("false") Boolean headless) {
        this.browserName = browserName;
        this.headless = headless;
        this.playwright = createPlaywright();
        this.browser = createBrowser();
        this.browserContext = createBrowserContext(this.browser);
        this.traceEnabled = traceEnabled;
        if (this.traceEnabled) {
            startTraceRecording();
        }
        this.page = browserContext.newPage();
        log.info("Playwright config has been set and created");
    }

    /**
     * In this method I create the playwright
     * (it can be created in the constructor, but I use a method in case if additional setup needed)
     */
    public Playwright createPlaywright() {
        return Playwright.create();
    }

    /**
     * In this method I create browser and options for running tests on these browsers
     */
    public Browser createBrowser() {
        Path downloadPath = Path.of(this.downloadPath);
        return switch (this.browserName) {
            case "chrome" -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setChannel("chrome") // It makes Playwright run Google Chrome (not Chromium)
                    .setHeadless(this.headless) // Visible or not visible mode for test executing
                    .setDevtools(false) // Show dev console or not during browser working
//                    .setSlowMo(6000) // For running tests in slow-motion mode
                    .setDownloadsPath(downloadPath)); // Path to save files from browser
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions()
                    .setChannel("firefox")
                    .setHeadless(this.headless)
                    .setDevtools(false)
                    .setDownloadsPath(downloadPath));
            case "safari" -> playwright.webkit().launch(new BrowserType.LaunchOptions()
                    .setChannel("webkit")
                    .setHeadless(this.headless)
                    .setDevtools(false)
                    .setDownloadsPath(downloadPath));
            default ->
                    throw new IllegalArgumentException(String.format("Browser %s is not supported", this.browserName));
        };
    }

    /**
     * Контекст в Playwright — это изолированная сессия браузера, похожая на отдельный пользовательский профиль.
     * Каждый контекст имеет свои cookies, localStorage, сессию и другие данные.
     */
    public BrowserContext createBrowserContext(Browser browser) {
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setViewportSize(1680, 772) // Размер окна браузера.
                .setIgnoreHTTPSErrors(true); // Указывает Playwright игнорировать ошибки, связанные с сертификатами HTTPS.
        return browser.newContext(options);
    }

    /**
     * Этот код включает трассировку в Playwright, что позволяет записывать действия браузера для анализа и отладки.
     */
    public void startTraceRecording() {
        this.browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true) // Сохраняет снапшоты DOM и позволяет просмотреть структуру страницы на каждом шаге.
                .setSources(false)); // Сохраняет исходный код скриптов, которые выполняются на странице.
    }
}
