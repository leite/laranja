package laranja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

import bencode.Encoder;

@SuppressWarnings("serial")
public class LaranjaServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    
    Encoder enc                        = null;
    Map<String, Object> dictionary     = null;
    Map<Object, Object> dictionary_two = null;
    List<Object> tuple                 = null;
    
    try {
    
      enc            = new Encoder();
      dictionary     = new HashMap<String, Object>();
      dictionary_two = new HashMap<Object, Object>();
      tuple          = new ArrayList<Object>();
      
      dictionary.put("jose", 250);
      dictionary.put("maria", true);

      dictionary_two.put("foos", "fizz");
      dictionary_two.put("bars", "buzz");
      dictionary_two.put(1, false);
      
      tuple.add("Foo");
      tuple.add("Bar");
      tuple.add(666);
      tuple.add("Fixme");
      tuple.add(dictionary);
      
      dictionary_two.put("big_tuple", tuple);
      
      resp.setContentType("text/plain");
    
      if( 
        (!enc.encode(true).equals("i1e")) ||
        (!enc.encode("Foo").equals("3:Foo")) ||
        (!enc.encode(212121212121212L).equals("i212121212121212e")) ||
      	(!enc.encode(89889).equals("i89889e")) ||
      	(!enc.encode(tuple).equals("l3:Foo3:Bari666e5:Fixmed4:josei250e5:mariai1eee")) ||
      	(!enc.encode(dictionary).equals("d4:josei250e5:mariai1ee")) ||
      	(!enc.encode(dictionary_two).equals("d9:big_tuplel3:Foo3:Bari666e5:Fixmed4:josei250e5:mariai1eeei1ei0e4:foos4:fizz4:bars4:buzze")) ) {
        resp.getWriter().println("Bencode assertation failure");
      }
      
    	resp.getWriter().println("Hello, world");
      
    } catch (Exception ex) {
      ex.printStackTrace(System.err);
    } finally {
      enc            = null;
      dictionary     = null;
      dictionary_two = null;
      tuple          = null;
    }
  }
}
