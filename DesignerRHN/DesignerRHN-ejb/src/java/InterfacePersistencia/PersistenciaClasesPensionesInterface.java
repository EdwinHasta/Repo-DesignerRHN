/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ClasesPensiones;
import java.math.BigInteger;
import java.util.List;

/**
 * Persistencia Clase Pensiones
 * @author AndresPineda
 */
public interface PersistenciaClasesPensionesInterface {
    /**
     * Crea un objeto de tipo Clases Pensiones
     * @param clasesPensiones Objeto a crear
     */
    public void crear(ClasesPensiones clasesPensiones);
    
    
    /**
     * Edita un objeto de tipo Clases Pensiones
     * @param clasesPensiones Objeto a editar
     */   
    public void editar(ClasesPensiones clasesPensiones);
    
    
    /**
     * Borrar un objeto de tipo Clases Pensiones
     * @param clasesPensiones Objeto a borrar
     */
    public void borrar(ClasesPensiones clasesPensiones);
    
    /**
     * Busca un objeto de tipo Clases Pensiones por medio de su ID
     * @param id Llave primaria ID 
     * @return clasesPensiones Objeto de tipo Clases Pensiones
     */
    public ClasesPensiones buscarClasePension(Object id);
    
    
    /**
     * Metodo que busca todos los elementos de la tabla Clases Pensiones
     * @return listaClasesPensiones Lista con todos los objetos de la tabla Clases Pensiones
     */
    public List<ClasesPensiones> buscarClasesPensiones();
    
    
    /**
     * Metodo que obtiene un objeto Clases Pensiones por su secuencia
     * @param secuencia
     * @return clasePension Elemento que cumple con la condicion de la secuencia
     */
    public ClasesPensiones buscarClasePensionSecuencia(BigInteger secuencia);
    
}
