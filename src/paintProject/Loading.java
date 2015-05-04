package paintProject;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Loading {
	
	 static final String SHAPE = "shape";
	 static final String TYPE = "type";
	 static final String X1 = "x1";
	 static final String X2 = "x2";
	 static final String Y1 = "y1";
	 static final String Y2 = "y2";
	 static final String FONT = "fontColor";
	 static final String FILL = "fillColor";
	  
	private String fileName;
	private ArrayList<ShapeSaving> shapes = new ArrayList<ShapeSaving>();
	
	public Loading(String s) {
		fileName = s + ".xml";
		readFile();
	}
	
	public void readFile() {
		
		try {
			ShapeSaving item = null;
			// First, create a new XMLInputFactory
		    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		    // Setup a new eventReader
		    InputStream in = new FileInputStream(fileName);
		    XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		    while (eventReader.hasNext()) {
		    	XMLEvent event = eventReader.nextEvent();
		    	if (event.isStartElement()) {
		    		StartElement startElement = event.asStartElement();
		    		// If we have an item element, we create a new item
		    		if (startElement.getName().getLocalPart() == (SHAPE)) {	
		    			item = new ShapeSaving();
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(TYPE)) {
		    			event = eventReader.nextEvent();
		    			item.setType(Integer.parseInt(event.asCharacters().getData()));
		    			continue;
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(X1)) {
		    			event = eventReader.nextEvent();
		    			item.setX1(Integer.parseInt(event.asCharacters().getData()));
		    			continue;
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(X2)) {
		    			event = eventReader.nextEvent();
		    			item.setX2(Integer.parseInt(event.asCharacters().getData()));
		    			continue;
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(Y1)) {
		    			event = eventReader.nextEvent();
		    			item.setY1(Integer.parseInt(event.asCharacters().getData()));
		    			continue;
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(Y2)) {
		    			event = eventReader.nextEvent();
		    			item.setY2(Integer.parseInt(event.asCharacters().getData()));
		    			continue;
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(FONT)) {
		    			event = eventReader.nextEvent();
		    			String s = event.asCharacters().getData();
		    			String[] split = s.split("-");
		    			int r = Integer.parseInt(split[0]);
		    			int g = Integer.parseInt(split[1]);
		    			int b = Integer.parseInt(split[2]);
		    			item.setFont(new Color(r, g, b));
		    			continue;
		    		}
		    		else if (event.asStartElement().getName().getLocalPart().equals(FILL)) {
		    			event = eventReader.nextEvent();
		    			String s = event.asCharacters().getData();
		    			if (!s.equals("null")) {
			    			String[] split = s.split("-");
			    			int r = Integer.parseInt(split[0]);
			    			int g = Integer.parseInt(split[1]);
			    			int b = Integer.parseInt(split[2]);
			    			item.setFill(new Color(r, g, b));
		    			}
		    			else 
		    				item.setFill(null);
		    			continue;
		    		}
		    	}
		        // If we reach the end of an item element, we add it to the list
		    	else if (event.isEndElement()) {
		        	EndElement endElement = event.asEndElement();
		        	if (endElement.getName().getLocalPart() == (SHAPE)){
		        		shapes.add(item);
		        	}
		        }
		    }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (XMLStreamException e) {
	      e.printStackTrace();
	    }
	}
	
	public ArrayList<ShapeSaving> getList() {
		return shapes;
	}
}
