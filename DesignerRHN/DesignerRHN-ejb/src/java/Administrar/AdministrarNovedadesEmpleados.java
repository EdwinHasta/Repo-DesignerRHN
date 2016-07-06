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
import InterfaceAdministrar.AdministrarSesionesInterface;
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
import javax.persistence.EntityManager;

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
    /**
     * Enterprise JavaBean.<br> Atributo que representa todo lo referente a la
     * conexión del usuario que está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;
    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    //Trae los empleados con Novedades dependiendo el Tipo de Trabajador que sea.

    @Override
    public List<PruebaEmpleados> empleadosNovedad() {

        List<Empleados> listaEmpleados = persistenciaEmpleados.empleadosNovedad(em);
        List<PruebaEmpleados> listaEmpleadosNovedad = new ArrayList<PruebaEmpleados>();
        for (int i = 0; i < listaEmpleados.size(); i++) {
            //Traer los datos del empleado con sueldo actual
            PruebaEmpleados p = persistenciaPruebaEmpleados.empleadosAsignacion(em, listaEmpleados.get(i).getSecuencia());
            //Traer el tipo de empleado de VWActualesTiposTrabajadores
//            String tipo = persistenciaVWActualesTiposTrabajadores.consultarTipoTrabajador(em, listaEmpleados.get(i).getSecuencia());

            if (p != null) {
//                p.setTipo(tipo);
                listaEmpleadosNovedad.add(p);
            } else {
                p = new PruebaEmpleados();
                p.setCodigo(listaEmpleados.get(i).getCodigoempleado());
                p.setId(listaEmpleados.get(i).getSecuencia());
                p.setNombre(listaEmpleados.get(i).getPersona().getNombreCompleto());
//                p.setTipo(tipo);
                p.setValor(null);
                listaEmpleadosNovedad.add(p);
            }

        }
        return listaEmpleadosNovedad;
    }

    //Trae las novedades del empleado cuya secuencia se envía como parametro//
    @Override
    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado) {
        System.out.println("novedadesEmpleado() secuenciaEmpleado: " +secuenciaEmpleado);
        try {
            return persistenciaNovedades.novedadesEmpleado(em, secuenciaEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesEmpleados.novedadesEmpleado" + e);
            return null;
        }
    }

    @Override
    public List<Novedades> todasNovedades(BigInteger secuenciaEmpleado) {
        try {
            return persistenciaNovedades.todasNovedadesEmpleado(em, secuenciaEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesEmpleados.todasNovedades" + e);
            return null;
        }
    }

    //Ver si está en soluciones formulas y de ser asi no borrarlo
    @Override
    public int solucionesFormulas(BigInteger secuenciaNovedad) {
        return persistenciaSolucionesFormulas.validarNovedadesNoLiquidadas(em, secuenciaNovedad);
    }

    @Override
    public String alias() {
        return persistenciaActualUsuario.actualAliasBD(em);
    }

    @Override
    public Usuarios usuarioBD(String alias) {
        return persistenciaUsuarios.buscarUsuario(em, alias);
    }

    //Procesa un solo empleado para volverlo Pruebaempleado
    @Override
    public PruebaEmpleados novedadEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaPruebaEmpleados.empleadosAsignacion(em, secuenciaEmpleado);
    }

    //Busca el empleado con el Id que se envía
    @Override
    public Empleados elEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaEmpleados.buscarEmpleado(em, secuenciaEmpleado);
    }

    //Listas de Conceptos, Formulas, Periodicidades, Terceros
    @Override
    public List<Conceptos> lovConceptos() {
        return persistenciaConceptos.buscarConceptos(em);
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

    // Que tipo de Trabajador es
    @Override
    public List<VWActualesTiposTrabajadores> tiposTrabajadores() {
        return persistenciaVWActualesTiposTrabajadores.tipoTrabajadorEmpleado(em);
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
}
