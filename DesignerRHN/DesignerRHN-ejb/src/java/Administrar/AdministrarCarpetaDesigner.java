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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author -Felipphe-
 */
@Stateless
public class AdministrarCarpetaDesigner implements AdministrarCarpetaDesignerInterface {
    
    @EJB
    PersistenciaModulosInterface persistenciaModulos;
    @EJB
    PersistenciaTablasInterface persistenciaTablas;
    @EJB
    PersistenciaPantallasInterface persistenciaPantallas;
    //persistencia de pruebas
    @EJB
    PersistenciaAficionesInterface persistenciaAficiones;
    public List<Modulos> listModulos;
    public List<Tablas> listTablas;
    public Pantallas pantalla;
    //pruebas para modificar
    public List<Aficiones> listAficiones;
    public Aficiones aficion;
    
    public List<Modulos> ConsultarModulos() {
        
        try {
            listModulos = persistenciaModulos.buscarModulos();
            return listModulos;
        } catch (Exception e) {
            listModulos = null;
            return listModulos;
        }
    }
    
    public List<Tablas> ConsultarTablas(BigInteger secuenciaMod) {
        
        try {
            listTablas = persistenciaTablas.buscarTablas(secuenciaMod);
            return listTablas;
        } catch (Exception e) {
            listTablas = null;
            return listTablas;
        }
    }
    
    public Pantallas ConsultarPantalla(BigInteger secuenciaTab) {
        
        try {
            pantalla = persistenciaPantallas.buscarPantalla(secuenciaTab);
            return pantalla;
        } catch (Exception e) {
            pantalla = null;
            return pantalla;
        }
    }
    
    public List<Aficiones> buscarAficiones() {
        
        try {
            listAficiones = persistenciaAficiones.buscarAficiones();
            return listAficiones;
        } catch (Exception e) {
            listAficiones = null;
            return listAficiones;
        }
    }
    
    public Aficiones unaAficion(BigInteger id) {
        aficion = persistenciaAficiones.buscarAficion(id);
        return aficion;
    }

//    public Aficiones buscarAfi(BigDecimal cod) {
    public List<Aficiones> buscarAfi() {
//        aficion = persistenciaAficiones.buscarAf(cod);
        listAficiones = persistenciaAficiones.buscarAf();
        return listAficiones;
    }
    
    public void modificarAficion(List<Aficiones> listAficiones) {
        for (int i = 0; i < listAficiones.size(); i++) {
            System.out.println("Modificando...");
            aficion = listAficiones.get(i);
            persistenciaAficiones.editar(aficion);
        }
    }
    
    public Integer sugerenciaCodigoAficiones() {
        if (persistenciaAficiones == null) {
            System.out.println("Persistencia vacia.");
        }
        Integer max = 0;
        Short respuesta;
        System.out.println("Hagalo!");
        respuesta = persistenciaAficiones.maximoCodigoAficiones();
        max = respuesta.intValue();
        max = max + 1;
        return max;
    }
    
    public void crearAficion(Aficiones aficion) {
        persistenciaAficiones.crear(aficion);
    }
    
    public void borrarAficion(Aficiones aficion) {
        persistenciaAficiones.borrar(aficion);
    }
    
    public Aficiones buscarAfiCodigo(Short cod) {
        aficion = persistenciaAficiones.buscarAficionCodigo(cod);
        return aficion;
    }
}
