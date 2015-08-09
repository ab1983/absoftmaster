import java.io.PrintWriter;
import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;


public class JavaScriptEmail {

	@Test
	public void test() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter script = new PrintWriter(stringWriter);
		script.println("try{");
		script.println("var title = new java.lang.String('Solicitação de Reserva');");
		script.println("var msg = new java.lang.String('O status atual é:'+'Respondido');");
		script.println("var destin = new ArrayList();");
		script.println("var name = 'Agnaldo';");
		script.println("var dest = 'agnaldo.luiz@algoboss.com';");
		script.println("var arrayObj = java.lang.reflect.Array.newInstance(java.lang.String,0);");
		script.println("var destArray = Arrays.asList(dest, name).toArray(arrayObj);");
		script.println("destin.add(destArray);");
		//Arrays.asList().toArray(java.lang.reflect.Array.newInstance(java.lang.String.class,0));
		script.println("com.algoboss.erp.face.GenericBean.sendEmail(destin, new java.lang.String('webmaster@algoboss.com'), new java.lang.String('AlgoBoss'), title, msg, null);");
		//com.algoboss.erp.face.GenericBean.sendEmail(destinatarios, emailRemet, nomeRemet, assunto, corpo, files)
		script.println("println('#### oi');");
		script.println("}catch (e) {");
			script.println("println(e);");
			script.println("println(e.stack);");
			script.println("println('Line Number:'+e.lineNumber);");
			script.println("println('Column Number:'+e.columnNumber);");
			script.println("println('File Name:'+e.fileName);");
		script.println("}");
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine jsEngine = manager.getEngineByExtension("js");
		try {
			jsEngine.eval("importPackage(java.util);");
			jsEngine.eval("importPackage(java.lang);");
			jsEngine.eval("importPackage(java.math);");
			jsEngine.eval("importPackage(javax.faces);");		
			System.out.println(stringWriter.toString());
			jsEngine.eval(stringWriter.toString());
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
