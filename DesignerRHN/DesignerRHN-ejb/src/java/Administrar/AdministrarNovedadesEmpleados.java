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
import Entidades.PruebaEmpleados;
import Entidades.Terceros;
import Entidades.Usuarios;
import Entidades.VWActualesTiposTrabajadores;
import InterfaceAdministrar.AdministrarNovedadesEmpleadosInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaPruebaEmpleadosInterface;
import InterfacePersistencia.PersistenciaSolucionesFormulasInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaUsuariosInterface;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarNovedadesEmpleados implements AdministrarNovedadesEmpleadosInterface {

    @EJB
    PersistenciaPruebaEmpleadosInterface persistenciaPruebaEmpleados;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;
    @EJB
    PersistenciaNovedadesInterface persistenciaNovedades;
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

    //Trae los empleados con Novedades dependiendo el Tipo de Trabajador que sea.

    public List<PruebaEmpleados> empleadosNovedad() {
        List<Empleados> listaEmpleados = persistenciaEmpleados.empleadosNovedad();
        List<PruebaEmpleados> listaEmpleadosNovedad = new ArrayList<PruebaEmpleados>();
        for (int i = 0; i < listaEmpleados.size(); i++) {
            PruebaEmpleados p = persistenciaPruebaEmpleados.empleadosAsignacion(listaEmpleados.get(i).getSecuencia());

            if (p != null) {
                listaEmpleadosNovedad.add(p);
            } else {
                p = new PruebaEmpleados();
                p.setCodigo(listaEmpleados.get(i).getCodigoempleado());
                p.setId(listaEmpleados.get(i).getSecuencia());
                p.setNombre(listaEmpleados.get(i).getPersona().getNombreCompleto());
                p.setValor(null);
                listaEmpleadosNovedad.add(p);
            }

        }
        return listaEmpleadosNovedad;
    }

    //Trae las novedades del empleado cuya secuencia se envía como parametro//
    @Override
    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            return persistenciaNovedades.novedadesEmpleado(secuenciaEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesEmpleados.novedadesEmpleado" + e);
            return null;
        }
    }
    
    public List<Novedades> todasNovedades(BigInteger secuenciaEmpleado){
        try {
            return persistenciaNovedades.todasNovedades(secuenciaEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesEmpleados.todasNovedades" + e);
            return null;
        }
    }
    
    //Ver si está en soluciones formulas y de ser asi no borrarlo
    public int solucionesFormulas(BigInteger secuenciaNovedad){
        return persistenciaSolucionesFormulas.validarNovedadesNoLiquidadas(secuenciaNovedad);
    }
    
    public String alias(){
        return persistenciaActualUsuario.actualAliasBD();
    }
    
    public Usuarios usuarioBD(String alias){
        return persistenciaUsuarios.buscarUsuario(alias);
    }
    
    //Procesa un solo empleado para volverlo Pruebaempleado
    @Override
    public PruebaEmpleados novedadEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaPruebaEmpleados.empleadosAsignacion(secuenciaEmpleado);
    }

    //Busca el empleado con el Id que se envía
    @Override
    public Empleados elEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaEmpleados.buscarEmpleado(secuenciaEmpleado);
    }

    //Listas de Conceptos, Formulas, Periodicidades, Terceros
    @Override
    public List<Conceptos> lovConceptos() {
        return persistenciaConceptos.buscarConceptos();
    }

    @Override
    public List<Formulas> lovFormulas() {
        return persistenciaFormulas.buscarFormulas();
    }

    @Override
    public List<Periodicidades> lovPeriodicidades() {
        return persistenciaPeriodicidades.buscarPeriodicidades();
    }

    @Override
    public List<Terceros> lovTerceros() {
        return persistenciaTerceros.buscarTerceros();
    }

    @Override
    public List<Empleados> lovEmpleados() {
        return persistenciaEmpleados.buscarEmpleados();
    }

    // Que tipo de Trabajador es
    @Override
    public List<VWActualesTiposTrabajadores> tiposTrabajadores() {
        return persistenciaVWActualesTiposTrabajadores.tipoTrabajadorEmpleado();
    }

    @Override
    public void borrarNovedades(Novedades novedades) {
        persistenciaNovedades.borrar(novedades);
    }

    @Override
    public void crearNovedades(Novedades novedades) {
        persistenciaNovedades.crear(novedades);
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
            persistenciaNovedades.editar(listaNovedadesModificar.get(i));
        }
    }
}
