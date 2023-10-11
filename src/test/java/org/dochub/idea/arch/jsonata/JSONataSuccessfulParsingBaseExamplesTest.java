package org.dochub.idea.arch.jsonata;

public class JSONataSuccessfulParsingBaseExamplesTest extends JSONataParsingTestAbstract {

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/jsonataTestData/base";
    }

    public void testAggregationQuery() {
        doSuccessfulParsingTest();
    }
    public void testArrayConstructors() {
        doSuccessfulParsingTest();
    }
    public void testBooleanExpressions() {
        doSuccessfulParsingTest();
    }
    public void testBooleanOperators() {doSuccessfulParsingTest();}
    public void testComments() {doSuccessfulParsingTest();}
    public void testComparisonExpressions() {doSuccessfulParsingTest();}
    public void testCompositionQuery() {doSuccessfulParsingTest();}
    public void testConditionalLogic() {doSuccessfulParsingTest();}
    public void testDateTimeProcessing() {doSuccessfulParsingTest();}
    public void testExpressions() {doSuccessfulParsingTest();}
    public void testFunctionDecl() {doSuccessfulParsingTest();}
    public void testGroupingQuery() {doSuccessfulParsingTest();}
    public void testJSONataPathProcessing() {doSuccessfulParsingTest();}
    public void testJSONLiterals() {doSuccessfulParsingTest();}
    public void testNumericExpressions() {doSuccessfulParsingTest();}
    public void testNumericOperators() {doSuccessfulParsingTest();}
    public void testObjectConstructors() {doSuccessfulParsingTest();}
    public void testOtherOperators() {doSuccessfulParsingTest();}
    public void testPathOperators() {doSuccessfulParsingTest();}
    public void testPredicateQueries() {doSuccessfulParsingTest();}
    public void testRegularExpressions() {doSuccessfulParsingTest();}
    public void testStringExpressions() {doSuccessfulParsingTest();}
    public void testVariables() {doSuccessfulParsingTest();}
    public void testHexCore() {doSuccessfulParsingTest();}
}
