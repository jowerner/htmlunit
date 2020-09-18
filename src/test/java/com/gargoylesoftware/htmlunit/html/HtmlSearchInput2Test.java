/*
 * Copyright (c) 2002-2020 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.html;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.SimpleWebTestCase;

/**
 * Tests for {@link HtmlSearchInput}.
 *
 * @author Anton Demydenko
 */
@RunWith(BrowserRunner.class)
public class HtmlSearchInput2Test extends SimpleWebTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void patternValidation() throws Exception {
        final String htmlContent
            = "<html>\n"
            + "<head></head>\n"
            + "<body>\n"
            + "<form id='form1'>\n"
            + "  <input type='search' pattern='[A-z]{2}[0-9]{4}' id='foo'>\n"
            + "</form>\n"
            + "</body></html>";

        final HtmlPage page = loadPage(htmlContent);

        final HtmlSearchInput input = (HtmlSearchInput) page.getElementById("foo");

        // empty
        assertFalse(input.isValid());
        // invalid
        input.setValueAttribute("qwerty");
        assertFalse(input.isValid());
        // valid
        input.setValueAttribute("AB1234");
        assertTrue(input.isValid());
    }

    /**
     * @throws Exception
     *         if the test fails
     */
    @Test
    public void testMaxLengthValidation() throws Exception {
        final String htmlContent = "<html>\n"
            + "<head></head>\n"
            + "<body>\n"
            + "<form id='form1'>\n"
            + "  <input type='search' id='foo' maxLength='3'>\n"
            + "</form>\n"
            + "</body></html>";

        final HtmlPage page = loadPage(htmlContent);

        final HtmlInput input = (HtmlInput) page.getElementById("foo");
        assertTrue(input.isValid());
        input.type("foo");
        assertTrue(input.isValid());
        input.type("bar");
        assertTrue(input.isValid());
        assertEquals("foo", input.getValueAttribute());
    }

    /**
     * @throws Exception
     *         if the test fails
     */
    @Test
    public void testMinLengthValidation() throws Exception {
        final String htmlContent = "<html>\n"
            + "<head></head>\n"
            + "<body>\n"
            + "<form id='form1'>\n"
            + "  <input type='search' id='foo' minLength='4'>\n"
            + "</form>\n"
            + "</body></html>";

        final HtmlPage page = loadPage(htmlContent);

        final HtmlInput input = (HtmlInput) page.getElementById("foo");
        assertFalse(input.isValid());
        input.type("foo");
        assertFalse(input.isValid());
        input.type("bar");
        assertTrue(input.isValid());
        assertEquals("foobar", input.getValueAttribute());
    }
}