package com.kencorhealth.campaign.seed;

import java.util.*;
import java.io.*;
import javax.script.*;
 
 
public class HashMapDemo {
 
        public static void main(String[] args) {
                HashMap<String, Object> hm = new HashMap();
                
                String AKey = "A.B.B";
 
                //hm.put("formData", Map.of("yes", "Yes"));
                hm.put("formYes", "formData.yes==\"Yes\"");
                hm.put(AKey, new Double(3434.34));
                hm.put("B", new Double(123.22));
                hm.put("C", new Double(1200.34));
                hm.put("D", new Double(99.34));
                hm.put("E", new Double(-19.34));
 
                for( String name: hm.keySet() )
                        System.out.println(name + ": "+ hm.get(name));
 
                // Increase A's balance by 1000
                double balance = ((Double)hm.get(AKey)).doubleValue();
                hm.put(AKey, new Double(balance + 1000));
                System.out.println("A's new account balance : " + hm.get(AKey));
 
                // Call JavaScript from Java
                try {   
                        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                        engine.eval("print('Hello World');");
                        engine.eval(new StringReader(script()));
                        Invocable invocable = (Invocable) engine;
                        Object result = invocable.invokeFunction("sayHello", "John Doe");
                        System.out.println(result);
                        System.out.println(result.getClass());
 
                        result = invocable.invokeFunction("prtHash", hm);
                        System.out.println(result);
                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                }
 
        }
        
        private static String script() {
            return "var sayHello = function(name) {\n" +
"        print('Hello, ' + name + '!');\n" +
"        return 'hello from javascript';\n" +
"};\n" +
" \n" +
"var prtHash = function(h) {\n" +
"        var formData = h.formData;\n" +
"        var formYes = h.formYes;\n" +
"        print('Eval = ' + eval(formYes));\n" +
"        print('h.A = ' + h['A.B.B']);\n" +
"        print('h.B = ' + h[\"B\"]);\n" +
"        print('h.C = ' + h.C);\n" +
"        print('h.D = ' + h[\"D\"]);\n" +
"        print('h.E = ' + h.E);\n" +
"};";
        }
}