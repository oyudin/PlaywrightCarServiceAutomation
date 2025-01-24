package org.example.carService.pages;

import com.microsoft.playwright.Page;
import org.example.carService.BaseUiTest;
/**
 * Интерфейс PageObject используется для создания страниц в автоматизированных UI-тестах.
 * Этот интерфейс предоставляет базовый метод {@link #newPage()}, который создает новый экземпляр
 * страницы с использованием конфигурации Playwright, заданной в {@link BaseUiTest}.
 * <p>
 *
 *  Этот интерфейс будет использоваться классом {@link AbstractBasePage}, тоесть в {@link AbstractBasePage} будет под
 *  капотом реализован метод default Page newPage() {
 *         return BaseUiTest.getPlaywrightConfig().getPage();
 *  Таким образом все страницы которые наследуют {@link AbstractBasePage}, будут так же использовать этот метод
 */
public interface PageObject {
    /**
     * Создает и возвращает новый объект страницы {@link Page}, используя конфигурацию Playwright.
     * @return объект {@link Page}, представляющий новую веб-страницу.
     */
    default Page newPage() {
        return BaseUiTest.getPlaywrightConfig().getPage();
    }
}
