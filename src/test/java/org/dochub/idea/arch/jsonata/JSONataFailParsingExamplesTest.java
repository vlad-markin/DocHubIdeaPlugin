package org.dochub.idea.arch.jsonata;

public class JSONataFailParsingExamplesTest extends JSONataParsingTestAbstract{

    @Override
    protected String getTestDataPath()  {
        return "src/test/testData/invalidPrograms";
    }

    public void testInvalidFunctionDecl() {
        doFailParsingTest();
    }
}
