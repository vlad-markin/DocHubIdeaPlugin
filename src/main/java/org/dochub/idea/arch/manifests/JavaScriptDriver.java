package org.dochub.idea.arch.manifests;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaScriptDriver {
    void test() throws ScriptException, FileNotFoundException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Invocable inv = (Invocable) engine;
        FileReader jsonata = new FileReader("jsonata.js");

// load the JSONata processor
        engine.eval(jsonata);

// read and JSON.parse the input data
        byte[] sample = new byte[0];
        try {
            sample = Files.readAllBytes(Paths.get("sample.json"));
            engine.put("input", new String(sample));
            Object inputjson = engine.eval("JSON.parse(input);");

// query the data
            String expression = "$sum(Account.Order.Product.(Price * Quantity))";  // JSONata expression
            Object expr = inv.invokeFunction("jsonata", expression);
            Object resultjson = inv.invokeMethod(expr, "evaluate", inputjson);

// JSON.stringify the result
            engine.put("resultjson", resultjson);
            Object result = engine.eval("JSON.stringify(resultjson);");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
