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
import generated.Videojuegos;

/**
 *
 * @author Diego Álvarez
 */
public class JaxB {
    Videojuegos misJuegos;
    public int abrir_XML_JAXB(File fichero){
        JAXBContext contexto;
        try{
            //Crea una instancia JAXB
            contexto = JAXBContext.newInstance(Videojuegos.class);
            //Crea un objeto Unmarsheller.
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (unmarshal) el fichero
            misJuegos = (Videojuegos) u.unmarshal(fichero);
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
        List<Videojuegos.Videojuego> Videojuegos = misJuegos.getVideojuego();
        //Recorre la lista para sacar los valores
        for(int i=0;i < Videojuegos.size();i++){
        cadena_resultado = cadena_resultado+"\n"+ "Publicado en:" + Videojuegos.get(i).getPublicadoEn();
        cadena_resultado = cadena_resultado + "\n"+ "Ha sido desarrollado por" +  Videojuegos.get(i).getDesarrolladoPor();
        cadena_resultado = cadena_resultado + "\n"+ "La clasificacion es de" +  Videojuegos.get(i).getClasificacion();
        cadena_resultado = cadena_resultado + "\n" + "El nombre del juego es: " + Videojuegos.get(i).getNombreJuego();
        cadena_resultado = cadena_resultado + "\n" + "El genero del juego es:  " + Videojuegos.get(i).getGenero();
        cadena_resultado = cadena_resultado + "\n" + "Plataforma/s: " + Videojuegos.get(i).getConsola();
        cadena_resultado = cadena_resultado +"\n----------------------";
    }
        return cadena_resultado;
    }
      
}
