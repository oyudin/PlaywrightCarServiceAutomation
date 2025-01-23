package org.example.carService;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.carService.config.PlaywrightConfig;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Это 2 класс который должен быть создан. В этом классе я прописываю как будут запускаться, завершаться UI тесты.
 * Так же указывается трейсинг и остальные параметры
 * Этот класс реализует базовый каркас для запуска и завершения UI-тестов с использованием Playwright.
 * Этот класс структурирует процесс выполнения UI-тестов, обеспечивает логирование, трассировку
 * и интеграцию с Allure для создания отчётов. Ключевые преимущества:
 */

@Slf4j
public abstract class BaseUiTest implements IHookable {

    private static final Boolean TRACE_ENABLED = true; // Этот флаг для включения или отключения трассировки

    /**
     * Используется для хранения конфигурации Playwright (PlaywrightConfig) в отдельном потоке.
     * Это полезно при параллельном выполнении тестов, чтобы каждый поток имел свой экземпляр Playwright.
     */
    protected static ThreadLocal<PlaywrightConfig> PLAYWRIGHT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * Устанавливает конфигурацию Playwright в поток-локальное хранилище.
     * Используется для передачи уникальных параметров Playwright в каждом потоке.
     */
    protected static void setPlaywrightConfig(PlaywrightConfig playwrightConfig) {
        PLAYWRIGHT_THREAD_LOCAL.set(playwrightConfig);
    }

    /**
     * Возвращает текущую конфигурацию Playwright из потока.
     * Полезно для получения доступа к Playwright в разных частях теста.
     */
    public static PlaywrightConfig getPlaywrightConfig() {
        return PLAYWRIGHT_THREAD_LOCAL.get();
    }

    /**
     * Проверяет, существует ли конфигурация (Object.nonNull).
     * Закрывает Playwright (getPlaywright().close()).
     * Удаляет объект из потока (PLAYWRIGHT_TREAD_LOCAL.remove()), чтобы освободить память.
     */
    protected static void closePlaywright() {
        if (Objects.nonNull(getPlaywrightConfig())) {
            PLAYWRIGHT_THREAD_LOCAL.get().getPlaywright().close();
            PLAYWRIGHT_THREAD_LOCAL.remove();
        }
    }

    /**
     * Главный метод интерфейса IHookable из TestNG, который запускает и позволяет перехватывать выполнение тестов.
     * Что происходит:
     * Выполняет тестовый метод через callBack.runTestMethod.
     * Если тест прошёл успешно, записывает лог и завершает трассировку (если она включена).
     * Если тест упал, вызывает метод failTest для обработки ошибки (скриншоты, трассировка, отчёт).
     *
     * @param callBack   объект, предоставляющий метод для выполнения теста.
     * @param testResult результат выполнения теста, включая его статус (успех или провал).
     */
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        log.info("Starting test method: {}", testResult.getMethod().getMethodName());
        try {
            callBack.runTestMethod(testResult);
            if (testResult.getThrowable() == null) {
                log.info("Test passed: {}", testResult.getMethod().getMethodName());
                if (TRACE_ENABLED) {
                    stopTracing(testResult.getMethod().getMethodName());
                }
            } else {
                failTest(testResult);
            }
        } catch (PlaywrightException e) {
            log.error("PlaywrightException encountered", e);
            failTest(testResult);
        } catch (Exception e) {
            log.error("Unexpected error during test execution", e);
            throw new RuntimeException("Test execution failed due to an unexpected error", e);
        }
    }

    /**
     * Помечен как @BeforeSuite — выполняется перед всеми тестами в сьюте.
     * В этот метод можно добавить все что должно быть выполнено перед началом тестов
     * Например устанавливает предварительные условия, например, время ожидания для PlaywrightAssertions.
     */
    @BeforeSuite
    public void setupPreconditions() {
        log.info("Setting preconditions for test suite");
        PlaywrightAssertions.setDefaultAssertionTimeout(5000);
    }

    /**
     * Назначение: выполняется перед каждым тестом.
     * Создаёт и сохраняет конфигурацию браузера на основе переданных параметров (название браузера, режим headless).
     *
     * Параметры передаются через TestNG XML-конфигурацию (testng.xml).
     * Если параметры не указаны в файле конфигурации, используются значения, заданные в аннотации @Optional.
     * Значения по умолчанию:
     * Если browserName отсутствует в XML, метод использует значение "chrome".
     * Если headless отсутствует в XML, метод использует значение "false".
     */
    @BeforeMethod
    @Parameters({"browserName", "headless"})
    public void setupBrowserConfig(@Optional("chrome") String browserName, @Optional("false") Boolean headless) {
        log.info("Configuring browser: {} with headless mode: {}", browserName, headless);
        setPlaywrightConfig(new PlaywrightConfig(browserName, TRACE_ENABLED, headless));
    }


    /**
     * Назначение: выполняется после каждого теста.
     * Закрывает браузерный контекст и освобождает ресурсы.
     * Сюда можно добавить все что должно быть выполнено после прохождения теста
     */
    @AfterMethod(alwaysRun = true)
    public void closeBrowserContext(ITestResult result) {
        log.info("Closing the browser context after test: {}", result.getMethod().getMethodName());
        closePlaywright();
    }

    /**
     * Назначение: возвращает путь для сохранения файла трассировки.
     */
    private Path getTraceFilePath(String methodName) {
        try {
            Path traceDir = Paths.get("target", "traces");
            if (!Files.exists(traceDir)) {
                Files.createDirectories(traceDir);
            }
            return traceDir.resolve(methodName + ".zip");
        } catch (Exception e) {
            log.error("Error creating trace directory", e);
            throw new RuntimeException("Failed to create trace directory", e);
        }
    }

    /**
     * Назначение: завершает трассировку и добавляет её в отчёт Allure.
     * Если трассировка отключена, добавляет HTML-источник страницы.
     * <p>
     * С помощью @SneakyThrows обработка исключений скрывается. Lombok автоматически добавляет необходимый код для
     * пробрасывания исключений. Это упрощает читаемость, особенно если вы уверены, что исключения маловероятны
     * или не критичны для работы программы.
     */
    @SneakyThrows
    private void addTraceToAllureReport(String methodName) {
        if (TRACE_ENABLED) {
            log.warn("Stop tracing for the test {}", methodName);
            var tracePath = getTraceFilePath(methodName);
            getPlaywrightConfig().getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(tracePath));
            Allure.addAttachment("trace", Files.newInputStream(tracePath));
        } else {
            Allure.addAttachment("source.html", "text/html", getPlaywrightConfig().getPage().content());
        }
    }

    /**
     * Завершает трассировку и сохраняет её в файл, прикрепляя его к Allure-отчёту.
     *
     * @param methodName имя тестового метода, для которого завершается трассировка.
     */
    private void stopTracing(String methodName) {
        try {
            Path tracePath = getTraceFilePath(methodName);
            getPlaywrightConfig().getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(tracePath));
            Allure.addAttachment("Trace for " + methodName, Files.newInputStream(tracePath));
        } catch (Exception e) {
            log.error("Failed to stop tracing for method: {}", methodName, e);
        }
    }


    /**
     * Назначение: делает скриншот текущей страницы.
     */
    @Attachment(value = "Failure screenshot in method {0}", type = "image/png")
    private byte[] captureScreenshot(String methodName) {
        return getPlaywrightConfig().getPage().screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    /**
     * Прикрепляет исходный HTML-код текущей страницы к Allure-отчёту.
     */
    private void attachPageSource() {
        try {
            String pageSource = getPlaywrightConfig().getPage().content();
            Allure.addAttachment("Page Source", "text/html", pageSource);
        } catch (Exception e) {
            log.error("Failed to attach page source", e);
        }
    }

    /**
     * Назначение: вызывается при провале теста.
     * Сохраняет трассировку и скриншот, добавляет их в отчёт Allure и пишет лог об ошибке.
     *
     * @param result объект, содержащий информацию о тесте, включая имя метода и исключение.
     */
    private void failTest(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        log.error("Test failed: {}", methodName);
        captureScreenshot(methodName);
        if (TRACE_ENABLED) {
            stopTracing(methodName);
        } else {
            attachPageSource();
        }
    }
}