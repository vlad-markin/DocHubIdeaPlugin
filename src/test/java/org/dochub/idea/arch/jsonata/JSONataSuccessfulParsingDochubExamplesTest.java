package org.dochub.idea.arch.jsonata;

public class JSONataSuccessfulParsingDochubExamplesTest extends JSONataParsingTestAbstract {
    @Override
    protected String getTestDataPath() { return "src/test/testData/jsonataTestData/dochub"; }

    public void testAutogeneration() {
        doSuccessfulParsingTest();
    }
    public void testBusinessEntities() {
        doSuccessfulParsingTest();
    }
    public void testC4modelNode() {
        doSuccessfulParsingTest();
    }
    public void testClusters() {doSuccessfulParsingTest();}
    public void testComponentsByThreads() {doSuccessfulParsingTest();}
    public void testComponentsExample() {doSuccessfulParsingTest();}
    public void testDatasetSystemsList() {doSuccessfulParsingTest();}
    public void testDifficultiesQuery() {doSuccessfulParsingTest();}
    public void testExampleQuery() {doSuccessfulParsingTest();}
    public void testForbiddenVolumes() {doSuccessfulParsingTest();}
    public void testJobTemplate() {doSuccessfulParsingTest();}
    public void testJsonschema() {doSuccessfulParsingTest();}
    public void testManifestExample() {doSuccessfulParsingTest();}
    public void testMultLookup() {doSuccessfulParsingTest();}
    public void testTemplateData() {doSuccessfulParsingTest();}
    public void testUserQuery() {doSuccessfulParsingTest();}
    public void testJsonataQueryExample() {doSuccessfulParsingTest();}
    public void testSequences() {doSuccessfulParsingTest();}
}
