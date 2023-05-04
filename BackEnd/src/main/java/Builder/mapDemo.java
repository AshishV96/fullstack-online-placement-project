package Builder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class mapDemo 
{
	public static void main(String[] args) {
		
		Map<String,String> m= new LinkedHashMap();
		m.put("name", "kishore");
		m.put("name1", "ashish");
		m.put("name2", "arvind");
		System.out.println(m);
		System.out.println(m.get("name1"));
	}

}
