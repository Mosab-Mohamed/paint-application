package paintProject;

import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Saving {

	private String fileName;
	private ArrayList<ShapeSaving> shapes;
	
	public Saving(ArrayList<ShapeSaving> s, String str) {
		this.shapes = s;
		this.fileName = str + ".xml";
		try {
			saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveConfig() throws Exception {
	
		// create an XMLOutputFactory
	    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	    // create XMLEventWriter
	    XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(fileName));
	    // create an EventFactory
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    XMLEvent end = eventFactory.createDTD("\n");
	    // create and write Start Tag
	    StartDocument startDocument = eventFactory.createStartDocument();
	    eventWriter.add(startDocument);
	    StartElement configStartElement = eventFactory.createStartElement("", "", "Shapes");
	    eventWriter.add(configStartElement);
	    for (ShapeSaving temp : shapes) {
		   
	    	configStartElement = eventFactory.createStartElement("", "", "shape");
		    eventWriter.add(configStartElement);
		    eventWriter.add(end);
		    
		    // Write the different nodes
		    createNode(eventWriter, "type", temp.getType() + "");
		    createNode(eventWriter, "x1", temp.getX1() + "");
		    createNode(eventWriter, "y1", temp.getY1() + "");
		    createNode(eventWriter, "x2", temp.getX2() + "");
		    createNode(eventWriter, "y2", temp.getY2() + "");
		    int r = temp.getFont().getRed();
		    int b = temp.getFont().getBlue();
		    int g = temp.getFont().getGreen();
		    createNode(eventWriter, "fontColor", r + "-" + g + "-" + b);
		    if (temp.getFill() != null) { 
			    r = temp.getFill().getRed();
			    b = temp.getFill().getBlue();
			    g = temp.getFill().getGreen();
			    createNode(eventWriter, "fillColor", r + "-" + g + "-" + b);
		    }
		    else {
		    	createNode(eventWriter, "fillColor", "null");
		    }
		    eventWriter.add(eventFactory.createEndElement("", "", "shape"));
		    eventWriter.add(end);
		    
	    }
	    eventWriter.add(eventFactory.createEndElement("", "", "Shapes"));
	    eventWriter.add(end);
	    eventWriter.add(eventFactory.createEndDocument());
	    eventWriter.close();
	  }
	
	private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
	    
		// create Start node
	    StartElement sElement = eventFactory.createStartElement("", "", name);	
	    eventWriter.add(tab);
	    eventWriter.add(sElement);
	    
	    // create Content
	    Characters characters = eventFactory.createCharacters(value);
	    eventWriter.add(characters);
	    
	    // create End node
	    EndElement eElement = eventFactory.createEndElement("", "", name);
	    eventWriter.add(eElement);
	    eventWriter.add(end);
	}
}
