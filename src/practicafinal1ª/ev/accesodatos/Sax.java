/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal1ª.ev.accesodatos;


import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Diego Álvarez
 */
public class Sax {
     SAXParser parser;
     ManejadorSAX sh;
     File ficheroXML;
    public int abrir_XML_SAX(File fichero){                 //Se crea un objeto SAXParser para interpretar el documento XML.
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            sh = new ManejadorSAX();
            ficheroXML = fichero;
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }


class ManejadorSAX extends DefaultHandler{
    int ultimoelement;
    String cadena_resultado="";
    
    public ManejadorSAX(){
        ultimoelement=0;
    }
    
    public void startElement(String uri, String localName, String qName, Attributes atts)throws SAXException{
    if (qName.equals("Videojuegos")){
                cadena_resultado = cadena_resultado + "Videojuegos encontrados:" 
                     + "\n" + "-------------------" + "\n" ;
            }
            
            
            else if (qName.equals("Videojuego")) {
                cadena_resultado = cadena_resultado + "\nPublicado en: "
                        + atts.getValue(atts.getQName(0)) + "\n";
                ultimoelement = 1;
            } else if (qName.equals("Videojuego")) {
                cadena_resultado = cadena_resultado + "\ndesarrollado por: "
                        + atts.getValue(atts.getQName(0)) + "\n";
                ultimoelement = 2;
            }else if (qName.equals("NombreJuego")) {
                ultimoelement = 3;
                cadena_resultado = cadena_resultado.trim() + "\n"+ "El nombre del videojuego es: ";
            } else if (qName.equals("Genero")) {
                ultimoelement = 4;
                cadena_resultado = cadena_resultado.trim() + "\n"+"El genero es: ";
            }else if (qName.equals("Consola")) {
                ultimoelement = 5;
                cadena_resultado = cadena_resultado.trim() + "\n"+"La/s plataforma/s es/son: ";
            }else if (qName.equals("Distribuidor")) {
                ultimoelement = 6;
                cadena_resultado = cadena_resultado.trim() + "\n"+"El distribuidor es: ";
            }else if (qName.equals("ModoJuego")) {
                ultimoelement = 7;
                cadena_resultado = cadena_resultado.trim() + "\n"+"El/Los modo/s de juego es/son: ";
            }else if (qName.equals("FechaLanzamiento")) {
                ultimoelement = 8;
                cadena_resultado = cadena_resultado.trim() + "\n"+"Su fecha de lanzamiento es: ";
            }
    }
     public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equals("Videojuego")) {
                System.out.println("Final de elemento.");
                cadena_resultado = cadena_resultado + "\n -------------------";
            }
        }
     
    public void characters(char[] ch, int start, int length) throws
                SAXException {
            if (ultimoelement == 2) {
                for (int i = start; i < length + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                }
            } else if (ultimoelement == 3) {
                for (int i = start; i < length + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                } 
                
            } else if (ultimoelement == 4) {
                for (int i = start; i < length + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                }
        }
    }
    
}

public String recorrerSAX() {
        try {
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException e) {
            e.printStackTrace();
            return "Error parseando SAX";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parseando SAX";
        }
    }
}