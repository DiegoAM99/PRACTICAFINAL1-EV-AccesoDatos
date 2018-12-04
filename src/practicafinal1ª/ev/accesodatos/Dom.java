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
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
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
    int conteo = 1;
    
    
   
    
    public int abrir_XML_DOM (File fichero){                                        //doc representa al arbol dom
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
                datos_nodo = procesarJuego(node);                               //Es un nodo juego
                    salida = salida + "\n" + "Publicado en:" + datos_nodo[0];
                    salida = salida + "\n" + "El nombre del juego es: "+ datos_nodo[1];
                    salida = salida + "\n" + "El genero del juego es: " + datos_nodo[2];
                    salida = salida + "\n" + "Plataforma/s: "+ datos_nodo[3];
                    salida = salida + "\n" + "Distribuidor: "+ datos_nodo[4];
                    salida = salida + "\n" + "Modo de Juego: "+ datos_nodo[5];
                    salida = salida + "\n" + "Fecha Lanzamiento: "+ datos_nodo[6];
                    salida = salida + "\n" + "Desarrollado por: "+ datos_nodo[7];
                    salida = salida + "\n" + "PEGI: "+ datos_nodo[8];
                    salida = salida + "\n-----------"; 
            }
            
        }
        return salida;
    }
    
    
    
    protected String[] procesarJuego(Node n){
        String datos[]= new String[9];
        Node ntemp = null;
        int contador = 1;
    
        datos[0]=n.getChildNodes().item(0).getNodeValue();                      //obtiene el valor del primer atributo del nodo(uno en este ejemplo)
        datos[1]=n.getChildNodes().item(0).getNodeValue();
        datos[2]=n.getChildNodes().item(0).getNodeValue();
        NodeList nodos = n.getChildNodes();                                     //obtiene los hijos del videojuego (nombrejuego, genero, consola, distribuidor, modojuego, fechalanzamiento) 
        
        for (int i=0; i < nodos.getLength(); i++){
            ntemp =nodos.item(i);
            
            if(ntemp.getNodeType() == Node.ELEMENT_NODE){
                datos[contador]= ntemp.getChildNodes().item(0).getNodeValue();  //IMPORTANTE para obtener el texto con cada campo se accede al nodo TEXT hijo de ntemp y se saca su valor
                contador++;
                    }
        }
        return datos;
    }
    
    
    
    
    
    public int annadirDOM(String nombreJuego, String genero, String consola, String distribuidor, String modojuego, String fechalanzamiento, String anno,
            String desarrollo, String clasificacion
   ){
        try{ 
            Node njuego = doc.createElement("NombreJuego");                         //Se crea un nodo tipo element con nombre 'juego'(<NombreJuego>)
            Node njuego_text=doc.createTextNode(nombreJuego);                       //Se crea un nodo tipo texto con el nombre del juego
            
            njuego.appendChild(njuego_text);                                  //Se añade el nodo de texto con el juego como hijo del elemento juego
            
            Node ngenero = doc.createElement("Genero");                           //Se hace lo mismo que con el genero del juego(<Genero>)
            Node ngenero_text = doc.createTextNode(genero);
            
            ngenero.appendChild(ngenero_text);
            
            Node nconsola = doc.createElement("Consola");                           //Se hace lo mismo que con NombreJuego a consola(<Consola>)
            Node nconsola_text = doc.createTextNode(consola);
            
            nconsola.appendChild(nconsola_text);
            
            Node ndistribuidor = doc.createElement("Distribuidor");                           //Se hace lo mismo que con el distribuidor
            Node ndistribuidor_text = doc.createTextNode(distribuidor);
            
            ndistribuidor.appendChild(ndistribuidor_text);
            
            Node nmodojuego = doc.createElement("ModoJuego");                           //Se hace lo mismo que con el modo de juego
            Node nmodojuego_text = doc.createTextNode(modojuego);
            
            nmodojuego.appendChild(nmodojuego_text);
            
            Node nfechalanzamiento = doc.createElement("FechaLanzamiento");                           //Se hace lo mismo que con la fecha de lanzamiento
            Node nfechalanzamiento_text = doc.createTextNode(fechalanzamiento);
            
            nfechalanzamiento.appendChild(nfechalanzamiento_text);
            
            Node nvideojuego=doc.createElement("Videojuego");                             //Se crea un nodo de tipo elemento(<videojuego>) 
            ((Element)nvideojuego).setAttribute("publicado_en", anno);
            ((Element)nvideojuego).setAttribute("desarrollado_por", desarrollo);
            ((Element)nvideojuego).setAttribute("Clasificacion", clasificacion);
            nvideojuego.appendChild(njuego);                                        //Se añade a videojuego el nodo juego, genero, consola, distribuidor, modojuego, fechalanzamiento  creados antes
            nvideojuego.appendChild(ngenero);
            nvideojuego.appendChild(nconsola);
            nvideojuego.appendChild(ndistribuidor);
            nvideojuego.appendChild(nmodojuego);
            nvideojuego.appendChild(nfechalanzamiento);
            
            
            //Al nuevo nodo videojuego se le añade los atributos
            
            Node raiz = doc.getChildNodes().item(0);                            //Se obtiene el primer nodo del documento y a el se le añade como hijo el nodo videojuego que ya tiene colgando todos sus hijos y atributos creados antes
            raiz.appendChild(nvideojuego);
            
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
    
    public String EjecutaXPath(String consulta) {
//        String countBooks = "count(//descendant::book)";
//        String getBooks = "/library/descendant::book";
//        String getAuthorWithId = "/library/descendant::author[@id = ";
//        
//        try {
//         XPathExpression exp = xpath.compile(consulta);
//        Number countNodes = (Number) expression.evaluate(xml, XPathConstants.NUMBER);
//        System.out.println("Number of books: " + countNodes.intValue());
//
//        expression = xPath.compile(getBooks);
//        NodeList books = (NodeList) expression.evaluate(xml, XPathConstants.NODESET);
//        for(int i = 0; i < books.getLength(); i++){
//
//            System.out.println( "Book #" + (i+1) );
//
//            NodeList bookAttributs = books.item(i).getChildNodes();
//            System.out.println( "Title: " + bookAttributs.item(0).getTextContent() );
//
//            expression = xPath.compile(getAuthorWithId + bookAttributs.item(1).getTextContent() + "]" );
//            Node author = (Node) expression.evaluate(xml, XPathConstants.NODE);
//
//            System.out.println( "Author: " + author.getTextContent() );
//}
//} catch (Exception e) {
//System.out.println(e.getMessage());
//e.printStackTrace(System.out);
//}

        try {
            //Crea el objeto XPath
            javax.xml.xpath.XPath xpath = XPathFactory.newInstance().newXPath();

            //Crea un XPathExpression con la consulta deseada
            XPathExpression exp = xpath.compile(consulta);
            
            //Ejecuta la consulta indicando que se ejecute sobre el DOM y que devolverá
            //el resultado como una lista de nodos.
            Object result = exp.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            //Ahora recorre la lista para sacar los resultados
            String salida = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                salida = salida + "\n"
                        + nodeList.item(i).getChildNodes().item(0).getNodeValue();
            }
            System.out.println(salida);
            return salida;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return null;
        }
    }
}
