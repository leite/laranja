package bencode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Encoder {
	
	private final List<String> acceptedTypes = Arrays.asList(new String[]{"ArrayList", "HashMap", "Integer", "Long", "String", "Boolean"});
	
	public String encode(Object o) throws NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		StringBuilder st = new StringBuilder();
		this.encode(o, st);
		String nnnn = st.toString();
		System.out.println(nnnn);
		return nnnn;
	}
	
	private void encode(Object o, StringBuilder st) throws NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if(o == null)
			throw new NullPointerException("Cannot encode null");;
		
		if(acceptedTypes.indexOf(o.getClass().getSimpleName()) > -1) {
			this.getClass().getDeclaredMethod("encode", o.getClass(), StringBuilder.class).invoke(this, o, st);
		} else {
			throw new IllegalArgumentException("Cannot encode: " + o.getClass());
		}
	}
	
	@SuppressWarnings("unused")
	private void encode(String o, StringBuilder st) throws UnsupportedEncodingException {
		st.append(o.length()); st.append(":"); st.append(o);
	}
	
	@SuppressWarnings("unused")
	private void encode(Long o, StringBuilder st) {
		st.append('i'); st.append(o); st.append('e');
	}
	
	@SuppressWarnings("unused")
	private void encode(Integer o, StringBuilder st) {
		st.append('i'); st.append(o); st.append('e');
	}
	
	@SuppressWarnings("unused")
	private void encode(HashMap<Object, Object> o, StringBuilder st) throws NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		st.append("d");
		Iterator<Object> inte = o.keySet().iterator();
		Object key    = null;
		while(inte.hasNext()) {
			key = inte.next();
			this.encode(key, st);
			this.encode(o.get(key), st);
		}
		st.append('e');
	}
	
	@SuppressWarnings("unused")
	private void encode(ArrayList<Object> o, StringBuilder st) throws NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		st.append("l");
		Iterator<Object> inte = o.iterator();
		while(inte.hasNext())
			this.encode(inte.next(), st);
		st.append("e");
	}
	
	@SuppressWarnings("unused")
	private void encode(Boolean o, StringBuilder st) {
		st.append('i'); st.append(o ? 1 : 0); st.append('e');
	}
}
