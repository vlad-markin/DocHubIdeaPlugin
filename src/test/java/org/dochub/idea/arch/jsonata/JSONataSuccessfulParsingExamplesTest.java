package org.dochub.idea.arch.jsonata;

public class JSONataSuccessfulParsingExamplesTest extends JSONataParsingTestAbstract {

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/jsonataTestData";
    }


    public void testFunctionDecl() {
        doSuccessfulParsingTest();
    }

    public void testPredicateQueries() {
        doSuccessfulParsingTest();
    }

    public void testExpressions() {
        doSuccessfulParsingTest();
    }

    public void testCompositionQuery() {doSuccessfulParsingTest();}

    public void testAggregation() {doSuccessfulParsingTest();}

    public void testComments() {doSuccessfulParsingTest();}

    public void testRegularExpressions() {doSuccessfulParsingTest();}
}
