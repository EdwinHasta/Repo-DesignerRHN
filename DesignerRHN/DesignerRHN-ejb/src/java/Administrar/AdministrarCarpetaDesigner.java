/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Aficiones;
import Entidades.Modulos;
import Entidades.Pantallas;
import Entidades.Tablas;
import InterfaceAdministrar.AdministrarCarpetaDesignerInterface;
import InterfacePersistencia.PersistenciaAficionesInterface;
import InterfacePersistencia.PersistenciaModulosInterface;
import InterfacePersistencia.PersistenciaPantallasInterface;
import InterfacePersistencia.PersistenciaTablasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'CarpetaDesigner'.
 * @author -Felipphe-
 */
@Stateless
public class AdministrarCarpetaDesigner implements AdministrarCarpetaDesignerInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaModulos'.
     */
    @EJB
    PersistenciaModulosInterface persistenciaModulos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaTablas'.
     */
    @EJB
    PersistenciaTablasInterface persistenciaTablas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaPantallas'.
     */
    @EJB
    PersistenciaPantallasInterface persistenciaPantallas;
    

//persistencia de pruebas
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaAficiones'.
     */
    @EJB
    PersistenciaAficionesInterface persistenciaAficiones;
    
    /**
     * Atributo que representa los Modulos guardados en la base de datos.
     */
    public List<Modulos> listModulos;
    /**
     * Atributo que representa las Tablas asociadas a un modulo.
     */
    public List<Tablas> listTablas;
    /**
     * Atributo que representa una pantalla asociada a una tabla.
     */
    public Pantallas pantalla;
    /**
     * Atributo que representa las Aficiones guardadas en la base de datos.
     */
    public List<Aficiones> listAficiones;
    /**
     * Atributo que representa la Aficion que será objetivo de una acción.
     */
    public Aficiones aficion;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Modulos> ConsultarModulos() {
        try {
            listModulos = persistenciaModulos.buscarModulos();
            return listModulos;
        } catch (Exception e) {
            listModulos = null;
            return listModulos;
        }
    }
    
    @Override
    public List<Tablas> ConsultarTablas(BigInteger secuenciaMod) {
        try {
            listTablas = persistenciaTablas.buscarTablas(secuenciaMod);
            return listTablas;
        } catch (Exception e) {
            listTablas = null;
            return listTablas;
        }
    }
    
    @Override
    public Pantallas ConsultarPantalla(BigInteger secuenciaTab) {
        try {
            pantalla = persistenciaPantallas.buscarPantalla(secuenciaTab);
            return pantalla;
        } catch (Exception e) {
            pantalla = null;
            return pantalla;
        }
    }
    
    @Override
    public List<Aficiones> buscarAficiones() {
        try {
            listAficiones = persistenciaAficiones.buscarAficiones();
            return listAficiones;
        } catch (Exception e) {
            listAficiones = null;
            return listAficiones;
        }
    }
    
    @Override
    public Aficiones unaAficion(BigInteger secuencia) {
        aficion = persistenciaAficiones.buscarAficion(secuencia);
        return aficion;
    }
    
    @Override
    public void modificarAficion(List<Aficiones> listAficiones) {
        for (int i = 0; i < listAficiones.size(); i++) {
            System.out.println("Modificando...");
            aficion = listAficiones.get(i);
            persistenciaAficiones.editar(aficion);
        }
    }
    
    @Override
    public Integer sugerenciaCodigoAficiones() {
        if (persistenciaAficiones == null) {
            System.out.println("Persistencia vacia.");
        }
        Integer max;
        Short respuesta;
        System.out.println("Hagalo!");
        respuesta = persistenciaAficiones.maximoCodigoAficiones();
        max = respuesta.intValue();
        max = max + 1;
        return max;
    }
    
    @Override
    public void crearAficion(Aficiones aficion) {
        persistenciaAficiones.crear(aficion);
    }
    
    @Override
    public void borrarAficion(Aficiones aficion) {
        persistenciaAficiones.borrar(aficion);
    }
    
    @Override
    public Aficiones buscarAfiCodigo(Short cod) {
        aficion = persistenciaAficiones.buscarAficionCodigo(cod);
        return aficion;
    }
}
