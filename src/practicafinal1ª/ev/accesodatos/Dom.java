/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal1ª.ev.accesodatos;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Diego Álvarez
 */
public class Dom {
    Document doc;
    int conteo;
    String salida;
    NodeList nodelist;
   
    
    public int abrir_XML_DOM (File fichero){                                        //doc representa al arbol dom
        doc = null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //Se crea un objeto DocumentBuider Factory
            factory.setIgnoringElementContentWhitespace(true);                      //Ignora los espacios en blanco
            DocumentBuilder builder=factory.newDocumentBuilder();                   //Crea un objeto DocumentBuider para cargar en el la estructura de arbol dom a partir del xml seleccionado
            doc = builder.parse(fichero);                                           //Interpreta (parsear) el documento xml(file) y genera el dom  equivalente
                return 0;                                                           //Ahora doc apunta el arbol dom listo para ser recorrido
        }
        catch (Exception e){
        e.printStackTrace();
        return -1;
        }
    }
    
    
    public String recorrerDOMyMostrar(Document doc){
    
        String datos_nodo[]=null;
        String salida="";
        Node node;
        
        Node raiz=doc.getFirstChild();                                          //obtiene el primer nodo del DOM(primer hijo)
        NodeList nodelist=raiz.getChildNodes();                                 //obtiene una lista de nodos con todos los nodos hijo del raiz
        for(int i=0; i<nodelist.getLength(); i++){                              //Procesa los nodos hijo
            node = nodelist.item(i);
            if (node.getNodeType()==Node.ELEMENT_NODE){
                datos_nodo = procesarJuego(node);                               //Es un nodo libro
                    salida = salida + "\n" + "El nombre del juego es: "+ datos_nodo[0];
                    salida = salida + "\n" + "El genero del juego es: " +datos_nodo[1];
                    salida = salida + "\n" + "Plataforma/s: "+datos_nodo[2];
                    salida = salida + "\n-----------"; 
            }
            
        }
        return salida;
    }
    
    
    
    protected String[] procesarJuego(Node n){
        String datos[]= new String[3];
        Node ntemp = null;
        int contador = 1;
    
        datos[0]=n.getChildNodes().item(0).getNodeValue();                      //obtiene el valor del primer atributo del nodo(uno en este ejempo)
        NodeList nodos = n.getChildNodes();                                     //obtiene los hijos del libro (titulo y autor) 
        
        for (int i=0; i < nodos.getLength(); i++){
            ntemp =nodos.item(i);
            
            if(ntemp.getNodeType() == Node.ELEMENT_NODE){
                datos[contador]= ntemp.getChildNodes().item(0).getNodeValue();  //IMPORTANTE para obtener el texto con el titulo y el autor se accede al nodo TEXT hijo de ntemp y se saca su valor
                contador++;
                    }
        }
        return datos;
    }
    
    
    
    
    
    public int añadirDOM(String nombreJuego, String genero, String consola, String distribuidor, String modojuego, String fechalanzamiento){
        try{
            Node njuego = doc.createElement("NombreJuego");                         //Se crea un nodo tipo element con nombre 'titulo'(<Titulo>)
            Node njuego_text=doc.createTextNode(nombreJuego);                       //Se crea un nodo tipo texto con el titulo del libro
            
            njuego.appendChild(njuego_text);                                  //Se añade el nodo de texto con el titulo como hijo del elemento titulo
            
            Node ngenero = doc.createElement("Genero");                           //Se ace lo mismo que con titulo a autor(<Autor>)
            Node ngenero_text = doc.createTextNode(genero);
            
            ngenero.appendChild(ngenero_text);
            
            Node nconsola = doc.createElement("Consola");                           //Se ace lo mismo que con titulo a autor(<Autor>)
            Node nconsola_text = doc.createTextNode(consola);
            
            nconsola.appendChild(nconsola_text);
            
            Node ndistribuidor = doc.createElement("Distribuidor");                           //Se ace lo mismo que con titulo a autor(<Autor>)
            Node ndistribuidor_text = doc.createTextNode(distribuidor);
            
            ndistribuidor.appendChild(ndistribuidor_text);
            
            Node nmodojuego = doc.createElement("ModoJuego");                           //Se ace lo mismo que con titulo a autor(<Autor>)
            Node nmodojuego_text = doc.createTextNode(modojuego);
            
            nmodojuego.appendChild(nmodojuego_text);
            
            Node nfechalanzamiento = doc.createElement("FechaLanzamiento");                           //Se ace lo mismo que con titulo a autor(<Autor>)
            Node nfechalanzamiento_text = doc.createTextNode(fechalanzamiento);
            
            nfechalanzamiento.appendChild(nfechalanzamiento_text);
            
            Node raiz = doc.getChildNodes().item(0);                            //Se obtiene el primer nodo del documento y a el se le añade como hijo el nodo libro que ya tiene colgando todos sus hijos y atributos creados antes
            raiz.appendChild(njuego);
            
            return 0;    
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    
    
    
    
    
    public int guardarDOMcomoFILE(String salida){
    try{
        //Crea un fichero llamado salida.xml
        File archivo_xml = new File(salida);                              //Crea un fichero llamado salida.xml
        OutputFormat format =new OutputFormat(doc);                             //Especifica el formato de salida
        format.setIndenting(true);                                              //Especifica que la salida este  indentada
        XMLSerializer serializer =new XMLSerializer(new FileOutputStream        //Escribe el contenido en el file    
        (archivo_xml),format);
        
        serializer.serialize(doc);
        
        return 0;  
    }
    catch(Exception e){
        return-1;
    }
    
    }
    
    public void modificarDOM( String oldT, String newT) {
        String util = "Titulo";
        Node node;
        //Inicializo el contador a 0 para que siempre este vacio al ejecutarlo
        conteo = 0;
        //Inicializo un nodelist que almacena todos los nodos existentes que coincidan con el titulo que le paso
        NodeList listaN = doc.getElementsByTagName(util);        
        //for que recorre el listaN
        for (int i = 0; i < listaN.getLength(); i++) {
            node = listaN.item(i);
        //Si los valores de titulo y node se igualan entra en el if
            if (node.getTextContent().equalsIgnoreCase(oldT)) {
        //Paso al node el nuevo titulo
                node.setTextContent(newT);
        //Sumo 1 en contador para indentificar cambio
                conteo++;
            }
        }
    }
}
