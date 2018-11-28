/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal1ª.ev.accesodatos;

import java.util.List;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Diego Álvarez
 */
public class JaxB {
    Libros misLibros;
    public int abrir_XML_JAXB(File fichero){
        JAXBContext contexto;
        try{
            //Crea una instancia JAXB
            contexto = JAXBContext.newInstance(Libros.class);
            //Crea un objeto Unmarsheller.
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (unmarshal) el fichero
            misLibros = (Libros) u.unmarshal(fichero);
            return 0;
        }catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }
    
    public String recorrerJAXByMostrar(){
        String datos_nodo[]=null;
        String cadena_resultado="";
        
        //Crea una lista con objetos de tipo libro
        List<Libros.Libro> Libros = misLibros.getLibro();
        //Recorre la lista para sacar los valores
        for(int i=0;i < Libros.size();i++){
        cadena_resultado = cadena_resultado+"\n"+ "Publicado en:" + Libros.get(i).getPublicadoEn();
        cadena_resultado = cadena_resultado + "\n"+ "El Titulo es" +  Libros.get(i).getTitulo();
        cadena_resultado = cadena_resultado + "\n"+ "El Autor es" +  Libros.get(i).getAutor();
        cadena_resultado = cadena_resultado + "\n" + "La Editorial es: " + Libros.get(i).getEditorial();
        cadena_resultado = cadena_resultado +"\n----------------------";
    }
        return cadena_resultado;
    }
}
