package paintProject;


import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Program extends JFrame {

	// our Buttons
    private JButton line;
	private JButton circle;
	private JButton arc;
	private JButton rectangle;
	private JButton triangle;
	private JButton ellipse;
	private JButton square;
	private JButton undo;
	private JButton redo;
	private JButton color;
	private JButton fontColor;
	private JButton fillColor;

	
	// our menu bar
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newItem = new JMenuItem("NEW");
	private JMenuItem importItem = new JMenuItem("IMPORT");
	private JMenuItem openItem = new JMenuItem("OPEN");
	private JMenuItem saveItem = new JMenuItem("SAVE");
	
	private JMenu editMenue = new JMenu("Edit");
	private JMenuItem moveItem = new JMenuItem("Move");
	private JMenuItem resizeItem = new JMenuItem("Rresize");
	private JMenuItem deleteItem = new JMenuItem("delete shape");

	// restore shapes and their attributes
	ArrayList<Shape2D> shapes = new ArrayList<Shape2D>();

	// we use it for undo and redo
	ArrayList<Shape2D> redoShapes = new ArrayList<Shape2D>();

	// we use it in saving
	ArrayList<ShapeSaving> list = new ArrayList<ShapeSaving>();

	

	Cursor curCursor;
	Rectangle frame ;
	Shape2D selectedShape = null;
	Shape2D resizedShape = null;
    Rectangle2D handleRectangle = null;
    Rectangle2D resizeRectangle = null;

	boolean fill = false , move =false , delete = false , resize =false ;
	boolean area1 = false , area2 = false , area3 = false , area4 = false ;

	int lastAction = 1;
	int currentAction = 1;
	int xStart , yStart , xEnd , yEnd;

	Color fillShape;
	Color strokeColor = Color.BLACK ;
	public static void main(String[] args) {

		Program myPrograme = new Program();
	}

	public Program(){

		this.setSize(1200,650);
		this.setBackground(Color.white);
		this.setTitle("Java Paint");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createVerticalBox();

        line = creatButton("./src/line.png",1 );
        circle = creatButton("./src/circle.png",2 );
        rectangle = creatButton("./src/rectangle.png",3 );
        triangle = creatButton("./src/triangle.png",4 );
        ellipse = creatButton("./src/ellipse.png",5 );
        square = creatButton("./src/square.png",6 );
        arc = creatButton("./src/Arc.jpg", 7);
        undo = creatButton("./src/undo.png",8);
        redo = creatButton("./src/redo.png",9);
        fontColor = creatButton("./src/Stroke.png",10);
        fillColor = creatButton("./src/Fill.png",11);
        
        arc.setVisible(false);
        
        box1.add(line);
        box1.add(circle);
        box1.add(rectangle);
        box1.add(triangle);
        box1.add(ellipse);
        box1.add(square);
        box1.add(arc);
        box1.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));

        box2.add(undo);
        box2.add(redo);
        box2.add(fontColor);
        box2.add(fillColor);
        box2.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));

        panel1.add(box1);
        panel2.add(box2);
//        panel3.add(new DrawingBoard());

        
        panel1.setBackground(Color.PINK);
        panel2.setBackground(Color.PINK);
//        panel3.setBackground(Color.WHITE);
        
        this.add(panel1, BorderLayout.PAGE_START);
        this.add(panel2, BorderLayout.EAST);
        this.add(new DrawingBoard(), BorderLayout.CENTER);

        fileMenu.add(newItem);
        fileMenu.add(importItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        
        editMenue.add(moveItem);
        editMenue.add(resizeItem);
        editMenue.add(deleteItem);
        menuBar.add(editMenue);
        

        moveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				move = true ;
				resize = false ;
				delete = false ;
				fill = false ;
				area1 = false ; area2 = false ; area3 = false ; area4 = false ;
			}
		});
        resizeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				move = false ;
				resize = true ;
				delete = false ;
				fill = false ;
				area1 = false ; area2 = false ; area3 = false ; area4 = false ;
			}
		});
        deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				move = false ;
				resize = false ;
				delete = true ;
				fill = false ;
				area1 = false ; area2 = false ; area3 = false ; area4 = false ;
			}
		});
        
        newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapes.clear();
				repaint();
			}
		});
        importItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arc.setVisible(true);
			}
		});
        saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.clear();
				String fileName = JOptionPane.showInputDialog("Enter a name to your file : ");
				save(fileName);
			}
		});
        openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog("Enter your file URL : ");
				File f = new File(fileName + ".xml");
				if (f.exists()) {
					list.clear();
					Loading l = new Loading(fileName);
					list = l.getList();
					System.out.println(list.size());
					loadShapes();
				}
				else 
					JOptionPane.showMessageDialog(null, "File not exist");
			}
		});

        this.setJMenuBar(menuBar);
        this.setVisible(true);
	}
	
	public JButton creatButton (String info,final int type){
    	JButton button = new JButton();
    	Icon butIcon = new ImageIcon(info);
        button.setIcon(butIcon);
        
        button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				move = false ;
				resize = false ;
				delete = false ;
				fill = false ;
				handleRectangle = null;
				resizeRectangle = null ;
				selectedShape = null ;
				resizedShape = null ;
				area1 = false ; area2 = false ; area3 = false ; area4 = false ;
				if(type == 8){
					if(!shapes.isEmpty()){
						redoShapes.add( shapes.remove(shapes.size()-1) );
						handleRectangle = null;
            	 		repaint();
					}
				}
				
				else if(type == 9){
					if(!redoShapes.isEmpty()){
						shapes.add( redoShapes.remove(redoShapes.size()-1) );
						handleRectangle = null;
            	 		repaint();
					}
				}
				else if(type == 10){
					Color c = JColorChooser.showDialog(null, "Choose Font Color ", strokeColor);
					strokeColor = c ;
				}
				else if(type == 11){
					Color c = JColorChooser.showDialog(null, "Choose fill Color ", fillShape);
					fillShape = c ;
					fill = true ;
				}
				else{
					currentAction = type ;
				}
				
			}
        });
        
    	return button ;
    }
	
	 private class DrawingBoard extends JComponent
     {
     	
             
             Point drawStart, drawEnd;

             public DrawingBoard()
             {
            	 setLayout(new BorderLayout());
            	 this.setBackground(Color.white);
            	 this.setLayout(new FlowLayout());
            	 
            	 this.addMouseListener(new MouseAdapter()
            	 {
            		 public void mousePressed(MouseEvent e)
                     {
            			 double area = 1000000000 ;
            			 boolean occupied =  false ;
            			 
            			 if(move){
            				 resizedShape = null ;
            				 resizeRectangle = null ;
            				 for (Shape2D s : shapes)
                             {
                           		if ( s.draw().contains( e.getX() , e.getY()) ){
                              		
                           			occupied = true ;
                           			if(s.area() < area){
                           				area = s.area() ;
                           				selectedShape = s ;
                           				handleRectangle = s.draw().getBounds2D();
                           			}
                            		
                            		xStart = e.getX() ;
                            		yStart = e.getY() ;
                            	}
                              	
                             }
            				 if(!occupied){
            					 handleRectangle = null;
            					 selectedShape = null ;
            				 }
                         		
            			 }
            			 
            			 else if(resize){
            				handleRectangle = null;
                      		selectedShape = null ;
                      		area = 1000000000;
                      		occupied = false ;
            				 
            				 if(resizedShape!=null){
            					 if(resizeArea1(e.getX(), e.getY(), resizedShape.draw().getBounds2D())){
            						 area1 = true ;
            						 area2 = false ;
            						 area3 = false ;
            						 area4 = false ;
            						 occupied = true ;
            					 }
            					 
            					 else if(resizeArea2(e.getX(), e.getY(), resizedShape.draw().getBounds2D())){
            						 area1 = false ;
            						 area2 = true ;
            						 area3 = false ;
            						 area4 = false ;
            						 occupied= true ;
            					 }
            					 
            					 else if(resizeArea3(e.getX(), e.getY(), resizedShape.draw().getBounds2D())){
            						 area1 = false ;
            						 area2 = false ;
            						 area3 = true ;
            						 area4 = false ;
            						 occupied = true ;
            					 }
            					 
            					 else if(resizeArea4(e.getX(), e.getY(), resizedShape.draw().getBounds2D())){
            						 area1 = false ;
            						 area2 = false ;
            						 area3 = false ;
            						 area4 = true ;
            						 occupied = true ;
            					 }
            				 }
            					 if(!occupied){
                					 resizedShape = null;
                					 resizeRectangle = null ;
                				 }
            					 else{
            						 xStart = e.getX() ;
            						 yStart = e.getY() ;
            					 }
            			 }
            			 
            			 else if(delete){
            				 handleRectangle = null;
                     		selectedShape = null ;
            			 }
            			 
            			 else if(fill){
            				 handleRectangle = null;
                     		selectedShape = null ;
            			 }
            				 
            			else{
            				selectedShape = null ;
            				handleRectangle = null;
                            drawStart = new Point(e.getX(), e.getY());
                     		drawEnd = drawStart;
            			}
                             repaint();
                     }

            		 public void mouseReleased(MouseEvent e)
            		 {
            			 Shape2D shape = null ; 
        				 
        				 if(move){
        					 resizedShape = null ;
        					 resizeRectangle = null ;
        					 if(selectedShape!=null){
        						 if ( selectedShape.draw().contains(e.getX(), e.getY()) ) {
            						 handleRectangle = selectedShape.draw().getBounds2D();
            					 }
        					 }
        					 selectedShape = null ;
        				 }
        				 
        				 else if(delete){
        					 resizedShape = null ;
        					 resizeRectangle = null ;
        					 handleRectangle = null;
                      		selectedShape = null ;
        				 }
        				 else if(resize){
        					 handleRectangle = null;
                      		selectedShape = null ;
                      		if(resizedShape!=null){
       						 if ( resizedShape.draw().contains(e.getX(), e.getY()) ) {
           						 resizeRectangle = resizedShape.draw().getBounds2D();
           					 }
       					 }
        				 }
        				 
        				 else if(fill){
        					 resizedShape = null ;
        					 resizeRectangle = null ;
        					 handleRectangle = null;
                      		selectedShape = null ;
        				 }
        				 
        				 else{
        					 resizedShape = null ;
        					 resizeRectangle = null ;
        					 handleRectangle = null;
                      		selectedShape = null ;
                      		shape = null ;
        					 switch(currentAction){
                    	 		case 1 : shape = new Line(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
                	 			
                    	 		case 2 : shape = new Circle(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
                     	 			
                    	 		case 3 : shape = new MyRectangle(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
             	 			
                    	 		case 4 : shape = new Triangle(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
             	 			
                    	 		case 5 : shape = new Ellipse(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
             	 			
                    	 		case 6 : shape = new Square(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
                     	 		
                    	 		case 7 : shape = new Arc(drawStart.x, drawStart.y,e.getX(), e.getY(),currentAction);
                    	 		break;
             	 			
                    	 		default : break ;
         				 }
        				 }
            			
        				 if(shape != null){
            					 if(!fill){
            						 shape.fontColor = strokeColor ;
            						 shapes.add(shape);

                					 redoShapes.clear();
            					 }
            				
        				 }
        				 
                         drawStart = null;
                         drawEnd = null;
                               
                         repaint();
                     }
            		 
            		 public void mouseClicked(MouseEvent e) {
            			 boolean happened = false ;
            			 double area = 1000000000 ;
            			 Shape2D shape = null ;
            			 
            			 if(move){
            				 for (Shape2D s : shapes)
                             {
                           		if ( s.draw().contains(  e.getX() , e.getY() ) ){
                              			
                              				if(s.area() < area){
                              					selectedShape = s ;
                              					happened = true ;
                              				}
                            	}
                              		
                             }
            			 }
            			 
            			 else if(fill){
            				 selectedShape = null ;
        					 area = 1000000000;
        					 for(Shape2D s : shapes){
        						 if(s.draw().contains(e.getX(),e.getY())){
        							 if(s.area() < area){
                           				area = s.area() ;
                           				shape = s ;
                           			}
        						 }
        					 }
        					 if(shape != null)
        						 shape.fillColor = fillShape ;
        				 }
            			 
            			 else if(resize){
            				 selectedShape = null ;
            				 happened = false ;
            				 area = 1000000000 ;
            				 for (Shape2D s : shapes)
                             {
                           		if ( s.draw().contains(  e.getX() , e.getY() ) ){
                           			happened = true ;
                           			if(s.area() < area){
                           				area = s.area() ;
                           				resizedShape = s ;
                           				resizeRectangle = s.draw().getBounds2D() ;
                           			}
                            	}
                              		
                             }
            			 }
            			 
            			 else if(delete){
            				 selectedShape = null ;
            				 int index = 0;
            				 area = 1000000000 ;
            				 happened = false ;
            				 for (int i=0 ; i<shapes.size() ; i++)
                             {
                           		
                           		if ( shapes.get(i).draw().contains( e.getX() , e.getY()) ){
                              		
                           			happened = true ;
                           			if(shapes.get(i).area() < area){
                           				area = shapes.get(i).area() ;
                           				index = i ;
                           			}
                           			
                           		}
                             }
            				 
            				 if(happened)
            					 redoShapes.add( shapes.remove(index) );
            			 }
            			 
            			 if(selectedShape!=null)
            				 handleRectangle = selectedShape.draw().getBounds2D();
            			 
            			 if(selectedShape == null) {
             		        	  handleRectangle = null;
             		     }
            		     repaint();
            		 }
                } );

                     this.addMouseMotionListener(new MouseMotionAdapter()
                     {
                       public void mouseDragged(MouseEvent e)
                       {
                    	   if(move && selectedShape!=null){
                    		   handleRectangle = null;
             		          xEnd = e.getX();
             		          yEnd = e.getY();

             		          selectedShape.x1 = selectedShape.x1 + xEnd - xStart ;
             		          selectedShape.y1 = selectedShape.y1 + yEnd - yStart;
             		          selectedShape.x2 = selectedShape.x2 + xEnd - xStart ;
             		          selectedShape.y2 = selectedShape.y2 + yEnd - yStart;

             		          xStart = xEnd;
             		          yStart = yEnd;
                    	   }
                    	   
                    	   else if(resize && resizedShape!=null){
                    		   resizeRectangle = null ;
                    		   xEnd = e.getX();
                    		   yEnd = e.getY();
                    		   
                    		   if(area1){
                    			   resizedShape.x2 = resizedShape.x2 + xEnd - xStart ;
                    			   resizedShape.y1 = resizedShape.y1 + yEnd - yStart;
                    		   }
                    		   
                    		   else if(area2){
                    			   resizedShape.x2 = resizedShape.x2 + xEnd - xStart ;
                    			   resizedShape.y2 = resizedShape.y2 + yEnd - yStart;
                    		   }
                    		   
                    		   else if(area3){
                    			   resizedShape.x1 = resizedShape.x1 + xEnd - xStart ;
                    			   resizedShape.y1 = resizedShape.y1 + yEnd - yStart;
                    		   }
                    		   
                    		   else if(area4){
                    			   resizedShape.x1 = resizedShape.x1 + xEnd - xStart ;
                    			   resizedShape.y2 = resizedShape.y2 + yEnd - yStart;
                    		   }
                    		   xStart = xEnd;
              		          yStart = yEnd;
                             		
                           }
                    	   
                    	   else if(fill){
                    		   
                    	   }
                    	   
                    	   else if(delete){
                    		   
                    	   }
                    	   
                    	   else{
                    		   drawEnd = new Point(e.getX(), e.getY());
                    	   }
                    	   
                    	   repaint();
                       }
                       
                       public void mouseMoved(MouseEvent e) {
                           if (selectedShape != null && move) {
                             if (selectedShape.draw().contains(e.getX(), e.getY())) {
                               curCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                             } else {
                               curCursor = Cursor.getDefaultCursor();
                             }
                           }
                           
                           else if (resizedShape != null && resize) {
                               
                            	   if(resizeArea1(e.getX(),e.getY(),resizedShape.draw().getBounds2D())){
                            		   curCursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                            	   }
                            	   else if(resizeArea2(e.getX(),e.getY(),resizedShape.draw().getBounds2D())){
                            		   curCursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                            	   }
                            	   else if(resizeArea3(e.getX(),e.getY(),resizedShape.draw().getBounds2D())){
                            		   curCursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                            	   }
                            	   else if(resizeArea4(e.getX(),e.getY(),resizedShape.draw().getBounds2D())){
                            		   curCursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                            	   }
                               
                               else {
                        		   curCursor = Cursor.getDefaultCursor();
                        	   }
                               
                             }
                           repaint();
                         }
                       
                     } );
         }
             
             public void paint(Graphics g)
             {
                     Graphics2D graphSettings = (Graphics2D)g;
                     graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

                     graphSettings.setStroke(new BasicStroke(2));
                     
                     graphSettings.setComposite(AlphaComposite.getInstance(
                             AlphaComposite.SRC_OVER, 1.00f));
                     
                     double[] areas = new double[shapes.size()];
                     
                     for(int i=0 ; i<areas.length ; i++){
                    	 areas[i] = shapes.get(i).area()  ;
                     }
                     Arrays.sort(areas);
                     
                     
                     for(int i=areas.length-1 ; i>=0 ; i--){
                    	 for (Shape2D s : shapes)
                         {
                    		 if(Double.toString(s.area()).equals(Double.toString(areas[i]))){
                    			  graphSettings.setPaint(s.fontColor);
                             
                    			  graphSettings.draw(s.draw());
                        	 	if(s.fillColor != null){
                         			graphSettings.setColor(s.fillColor);
                           			graphSettings.fill(s.draw());
                        	 	}
                    		 }
                        	
                        	 
                         	
                         	
                         }
                     }
                     

                     if (curCursor != null)
                         setCursor(curCursor);
                     
                     if (handleRectangle != null) {
                         drawHighlightSquares(graphSettings, handleRectangle);
                       }
                     
                     if (resizeRectangle != null) {
                         drawHighlightSquares2(graphSettings, resizeRectangle);
                       }
                     
                     
                     if (drawStart != null && drawEnd != null && !fill)
                     {
                     	// Makes the guide shape transparent
                         
                         graphSettings.setComposite(AlphaComposite.getInstance(
                                 AlphaComposite.SRC_OVER, 0.50f));
                         
                     	graphSettings.setPaint(Color.GRAY);
                     	
                     	Shape2D shape = null ; 
                     	switch(currentAction){
                	 	case 1 : shape = new Line(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
  
                	 			break;
                	 			
                	 	case 2 : shape = new Circle(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
        	 			break;
                	 			
                	 	case 3 : shape = new MyRectangle(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
        	 			break;
        	 			
                	 	case 4 : shape = new Triangle(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
        	 			break;
        	 			
                	 	case 5 : shape = new Ellipse(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
        	 			break;
        	 			
                	 	case 6 : shape = new Square(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
        	 			break;
        	 			
                	 	case 7 : shape = new Arc(drawStart.x, drawStart.y,drawEnd.x, drawEnd.y,currentAction);
            	 		break;
        	 			
        	 			default : break ;
                	 	}
                        
                     	graphSettings.draw(shape.draw());
                     }
       
             }
             
             public void drawHighlightSquares(Graphics2D g2D, Rectangle2D r) {
                 double x = r.getX();
                 double y = r.getY();
                 double w = r.getWidth();
                 double h = r.getHeight();
                 g2D.setColor(Color.GREEN);

                 g2D.fill(new Rectangle.Double(x - 3.0, y - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x + w - 3.0, y - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x - 3.0, y + h * 0.5 - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x + w - 3.0, y + h * 0.5 - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x - 3.0, y + h - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y + h - 3.0, 6.0, 6.0));
                 g2D.fill(new Rectangle.Double(x + w - 3.0, y + h - 3.0, 6.0, 6.0));
               }
             
             public void drawHighlightSquares2(Graphics2D g2D, Rectangle2D r) {
                 double x = r.getX();
                 double y = r.getY();
                 double w = r.getWidth();
                 double h = r.getHeight();
                 g2D.setColor(Color.BLUE);

                 g2D.fill(new Rectangle.Double(x , y , 12.0, 12.0));
                 g2D.fill(new Rectangle.Double(x + w -12, y , 12.0, 12.0));
                 g2D.fill(new Rectangle.Double(x, y + h -12 , 12.0, 12.0));
                 g2D.fill(new Rectangle.Double(x + w-12, y + h -12, 12.0, 12.0));
             }
             
             boolean resizeArea1(int x, int y,Rectangle2D bound){
            	 return (x>bound.getX() && x<bound.getX()+12 && y>bound.getY() && y<bound.getY()+12) ;
            	 
             }
             
             boolean resizeArea2(int x, int y,Rectangle2D bound){
            	 return (x>bound.getX() && x<bound.getX()+12 && y<bound.getHeight()+bound.getY() && y>bound.getHeight()+bound.getY()-12);
             }
             
             boolean resizeArea3(int x, int y,Rectangle2D bound){
            	 return (x<bound.getWidth()+bound.getX() && x>bound.getWidth()+bound.getX()-12 && y>bound.getY() && y<bound.getY()+12);
             }
             
             boolean resizeArea4(int x, int y,Rectangle2D bound){
            	 return (x<bound.getWidth()+bound.getX() && x>bound.getWidth()+bound.getX()-12 && y<bound.getHeight()+bound.getY() && y>bound.getHeight()+bound.getY()-12 );
             }
             
//             boolean xBoundCheck(int value){
//            	 return value>frame.x && value<frame.width ;
//             }
//             
//             boolean yBoundCheck(int value){
//            	 return value>frame.y && value<frame.height ;
//             }
     }
	 
	 // save the shapes to xml file
	 public void save(String fileName) {

		 for(int i = 0; i < shapes.size(); i++) {

			 ShapeSaving temp = new ShapeSaving();

			 Shape2D shape = shapes.get(i);
			 temp.setType(shape.type);
			 temp.setFill(shape.fillColor);
			 temp.setFont(shape.fontColor);
			 temp.setX1(shape.x1);
			 temp.setY1(shape.y1);
			 temp.setX2(shape.x2);
			 temp.setY2(shape.y2);

			 list.add(temp);
		 }
		 Saving s = new Saving(list, fileName);
	}
	 
	public void loadShapes(){
	
		shapes.clear();
		Shape2D shape = null;
		
		for(ShapeSaving s : list){
			switch(s.getType()){
			case 1: shape = new Line(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;

			case 2: shape = new Circle(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;

			case 3: shape = new MyRectangle(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;

			case 4: shape = new Triangle(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;

			case 5: shape = new Ellipse(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;

			case 6: shape = new Square(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;

			case 7: shape = new Arc(s.getX1(), s.getY1(), s.getX2(), s.getY2(), s.getType());
			break;
			
			default: break;
			}
			shape.fontColor = s.getFont();
			shape.fillColor = s.getFill();
			shapes.add(shape);
		 }
		repaint();
	 }
}
             

