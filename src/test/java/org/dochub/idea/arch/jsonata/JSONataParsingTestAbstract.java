package org.dochub.idea.arch.jsonata;

import com.intellij.testFramework.ParsingTestCase;

public abstract class JSONataParsingTestAbstract extends ParsingTestCase {

    public JSONataParsingTestAbstract() {
        super("", "jsonata", new JSONataParserDefinition());
    }

    protected void doSuccessfulParsingTest() {
        doTest(false, false);
        ensureNoErrorElements();
    }

    protected void doFailParsingTest() {
        boolean parsingFail = false;
        try {
            doSuccessfulParsingTest();
        } catch (AssertionError ignored) {
            parsingFail = true;
        }
        if (!parsingFail) {
            fail("Successful parse file " + myFile.getName());
        }
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }

    @Override
    protected abstract String getTestDataPath();
}
