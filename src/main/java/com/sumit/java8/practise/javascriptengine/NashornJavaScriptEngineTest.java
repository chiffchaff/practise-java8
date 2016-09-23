package com.sumit.java8.practise.javascriptengine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/**
 * Java 8 comes with new Nashorn JavaScript engine which allows developing 
 * and running certain kinds of JavaScript applications on JVM. 
 * Nashorn JavaScript engine is just another implementation of javax.script.ScriptEngine 
 * and follows the same set of rules, permitting Java and JavaScript interoperability. 
 * @author sumijaiswal
 *
 */
public class NashornJavaScriptEngineTest {
	public static void main(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName( "JavaScript" );
		System.out.println( engine.getClass().getName() );
		try {
			System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
