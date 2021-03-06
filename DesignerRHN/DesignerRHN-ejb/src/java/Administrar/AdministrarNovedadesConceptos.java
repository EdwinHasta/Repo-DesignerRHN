/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Formulas;
import Entidades.Novedades;
import Entidades.Periodicidades;
import Entidades.Terceros;
import Entidades.Usuarios;
import InterfaceAdministrar.AdministrarNovedadesConceptosInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaSolucionesFormulasInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaUsuariosInterface;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;


/**
 *
 * @author user
 */
@Stateful
public class AdministrarNovedadesConceptos implements AdministrarNovedadesConceptosInterface {
    
    @EJB
    PersistenciaNovedadesInterface persistenciaNovedades;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaUsuariosInterface persistenciaUsuarios;
    @EJB
    PersistenciaSolucionesFormulasInterface persistenciaSolucionesFormulas;
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    

    //Trae las novedades del empleado cuya secuencia se envía como parametro//
    public List<Novedades> novedadesConcepto(BigInteger secuenciaConcepto) {
        try {
            return persistenciaNovedades.novedadesConcepto(em, secuenciaConcepto);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesConceptos.conceptosNovedades" + e);
            return null;
        }
    }
    
    //Listas de Conceptos, Formulas, Periodicidades, Terceros

    public List<Conceptos> Conceptos() {
        return persistenciaConceptos.novedadConceptos(em);
    }
    
    public List<Terceros> Terceros() {
        return persistenciaTerceros.buscarTerceros(em);
    }

    @Override
    public List<Formulas> lovFormulas() {
        return persistenciaFormulas.buscarFormulas(em);
    }

    @Override
    public List<Periodicidades> lovPeriodicidades() {
        return persistenciaPeriodicidades.consultarPeriodicidades(em);
    }

    @Override
    public List<Terceros> lovTerceros() {
        return persistenciaTerceros.buscarTerceros(em);
    }

    @Override
    public List<Empleados> lovEmpleados() {
        return persistenciaEmpleados.empleadosNovedad(em);
    }
    
    //Ver si está en soluciones formulas y de ser asi no borrarlo
    public int solucionesFormulas(BigInteger secuenciaNovedad){
        return persistenciaSolucionesFormulas.validarNovedadesNoLiquidadas(em, secuenciaNovedad);
    }
    //Usuarios
    public String alias(){
        return persistenciaActualUsuario.actualAliasBD(em);
    }
    
    public Usuarios usuarioBD(String alias){
        return persistenciaUsuarios.buscarUsuario(em, alias);
    }
    
    @Override
    public void borrarNovedades(Novedades novedades) {
        persistenciaNovedades.borrar(em, novedades);
    }

    @Override
    public void crearNovedades(Novedades novedades) {
        persistenciaNovedades.crear(em, novedades);
    }
    
    @Override
    public void modificarNovedades(List<Novedades> listaNovedadesModificar) {
        for (int i = 0; i < listaNovedadesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaNovedadesModificar.get(i).getTercero().getSecuencia() == null) {
                listaNovedadesModificar.get(i).setTercero(null);
            }
            if (listaNovedadesModificar.get(i).getPeriodicidad().getSecuencia() == null) {
                listaNovedadesModificar.get(i).setPeriodicidad(null);
            }
            if (listaNovedadesModificar.get(i).getSaldo() == null) {
                listaNovedadesModificar.get(i).setSaldo(null);
            }
            if (listaNovedadesModificar.get(i).getUnidadesparteentera() == null) {
                listaNovedadesModificar.get(i).setUnidadesparteentera(null);
            }
            if (listaNovedadesModificar.get(i).getUnidadespartefraccion() == null) {
                listaNovedadesModificar.get(i).setUnidadespartefraccion(null);
            }
            persistenciaNovedades.editar(em, listaNovedadesModificar.get(i));
        }
    }
    
    public List<Novedades> todasNovedadesConcepto(BigInteger secuenciaConcepto){
        try {
            return persistenciaNovedades.todasNovedadesConcepto(em, secuenciaConcepto);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesConceptos.todasNovedadesConcepto" + e);
            return null;
        }
    }
}
