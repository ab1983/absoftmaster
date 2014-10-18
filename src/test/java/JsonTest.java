import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.google.gson.Gson;


public class JsonTest {

	@Test
	public void test() {
		String str = "[1,{name:['teste','teste2']},3,4,5]";
		//str="[%DATA,{´´PRESENÇALOCAL:[1,2,3]}]";
		str = "[%DATA,{´´PRESENÇA LOCAL:[{`OBRAS:[ID DA OBRA,NOME DA OBRA,{`CLIENTE:[NOME]}]},{`OCORRÊNCIA PRESENÇA:[NOME,_DESCRIÇÃO,!PRESENTE,CÓDIGO]},{`FUNCIONÁRIO:[NOME,{`FUNÇÃO:[NOME]}]}]}]";
		Gson gson = new Gson();
		try {
			Object[] obj = gson.fromJson(str.replace(' ', Character.toChars(0)[0]), Object[].class);
			System.out.println(Arrays.toString(obj).replace(Character.toChars(0)[0], ' '));
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		fail("Not yet implemented");
	}

}
