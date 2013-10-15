package Administrar;

import Entidades.Empleados;
import Entidades.MotivosCambiosSueldos;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import Entidades.TiposEntidades;
import Entidades.TiposSueldos;
import Entidades.VigenciasAfiliaciones;
import Entidades.VigenciasSueldos;
import InterfaceAdministrar.AdministrarVigenciasSueldosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaMotivosCambiosSueldosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTercerosSucursalesInterface;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import InterfacePersistencia.PersistenciaTiposSueldosInterface;
import InterfacePersistencia.PersistenciaVigenciasAfiliacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasSueldosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarVigenciasSueldos implements AdministrarVigenciasSueldosInterface{

    @EJB
    PersistenciaVigenciasSueldosInterface persistenciaVigenciasSueldos;
    @EJB
    PersistenciaVigenciasAfiliacionesInterface persistenciaVigenciasAfiliaciones;
    @EJB
    PersistenciaTiposSueldosInterface persistenciaTiposSueldos;
    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades;
    @EJB
    PersistenciaMotivosCambiosSueldosInterface persistenciaMotivosCambiosSueldos;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaTercerosSucursalesInterface persistenciaTercerosSucursales;
    //Vigencias Sueldos
    List<VigenciasSueldos> listVigenciasSueldos;
    VigenciasSueldos vigenciaSueldo;
    //Vigencias Afilicaiones
    List<VigenciasAfiliaciones> listVigenciasAfiliaciones;
    VigenciasAfiliaciones vigenciaAfiliacion;
    //Motivos Cambios Sueldos
    List<MotivosCambiosSueldos> listMotivosCambiosSueldos;
    //Tipos Sueldos
    List<TiposSueldos> listTiposSueldos;
    //Terceros
    List<Terceros> listTerceros;
    //Tipo Entidad
    List<TiposEntidades> listTiposEntidades;
    //Empleado
    Empleados empleado;
    //Tercertos Sucursales
    List<TercerosSucursales> listTercerosSucursales;

    @Override
    public List<VigenciasSueldos> VigenciasSueldosEmpleado(BigInteger secEmpleado) {
        try {
            listVigenciasSueldos = persistenciaVigenciasSueldos.buscarVigenciasSueldosEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Sueldos (VigenciasSueldosEmpleado)");
            listVigenciasSueldos = null;
        }
        return listVigenciasSueldos;
    }
    
    @Override
    public List<VigenciasSueldos> VigenciasSueldosActualesEmpleado(BigInteger secEmpleado) {
        try {
            listVigenciasSueldos = persistenciaVigenciasSueldos.buscarVigenciasSueldosEmpleadoRecientes(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Sueldos (VigenciasSueldosActualesEmpleado)");
            listVigenciasSueldos = null;
        }
        return listVigenciasSueldos;
    }


    @Override
    public void modificarVS(List<VigenciasSueldos> listVSModificadas) {
        try {
            for (int i = 0; i < listVSModificadas.size(); i++) {
                System.out.println("Modificando...");
                if(listVSModificadas.get(i).getMotivocambiosueldo().getSecuencia()==null){
                    listVSModificadas.get(i).setMotivocambiosueldo(null);
                }
                if(listVSModificadas.get(i).getTiposueldo().getSecuencia()==null){
                    listVSModificadas.get(i).setTiposueldo(null);
                }
                vigenciaSueldo = listVSModificadas.get(i);
                persistenciaVigenciasSueldos.editar(vigenciaSueldo);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVS AdmiVigenciasSueldos");
        }
    }

    @Override
    public void borrarVS(VigenciasSueldos vigenciasSueldos) {
        try {
            persistenciaVigenciasSueldos.borrar(vigenciasSueldos);
        } catch (Exception e) {
            System.out.println("Error borrarVS AdmiVigenciasSueldos");
        }
    }

    @Override
    public void crearVS(VigenciasSueldos vigenciasSueldos) {
        try {
            persistenciaVigenciasSueldos.crear(vigenciasSueldos);
        } catch (Exception e) {
            System.out.println("Error crearVS AdmiVigenciasSueldos");
        }
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            System.out.println("Error buscarEmpleado AdmiVigenciasSueldos");
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<VigenciasAfiliaciones> VigenciasAfiliacionesVigencia(BigInteger secVigencia) {
        try {
            listVigenciasAfiliaciones = persistenciaVigenciasAfiliaciones.buscarVigenciasAfiliacionesVigenciaSecuencia(secVigencia);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Afiliaciones (VigenciasSueldosEmpleado)");
            listVigenciasAfiliaciones = null;
        }
        return listVigenciasAfiliaciones;
    }

    @Override
    public void modificarVA(List<VigenciasAfiliaciones> listVAModificadas) {
        try {
            for (int i = 0; i < listVAModificadas.size(); i++) {
                System.out.println("Modificando...");
                if(listVAModificadas.get(i).getTipoentidad().getSecuencia()==null){
                    listVAModificadas.get(i).setTipoentidad(null);
                }
                if(listVAModificadas.get(i).getTercerosucursal().getTercero()==null){
                    listVAModificadas.get(i).getTercerosucursal().setTercero(null);
                }
                vigenciaAfiliacion = listVAModificadas.get(i);
                persistenciaVigenciasAfiliaciones.editar(vigenciaAfiliacion);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVA AdmiVigenciasSueldos");
        }
    }

    @Override
    public void borrarVA(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            persistenciaVigenciasAfiliaciones.borrar(vigenciasAfiliaciones);
        } catch (Exception e) {
            System.out.println("Error borrarVA AdmiVigenciasSueldos");
        }
    }

    @Override
    public void crearVA(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            persistenciaVigenciasAfiliaciones.crear(vigenciasAfiliaciones);
        } catch (Exception e) {
            System.out.println("Error crearVA AdmiVigenciasSueldos");
        }
    }

    @Override
    public List<TiposSueldos> tiposSueldos() {
        try {
            listTiposSueldos = persistenciaTiposSueldos.buscarTiposSueldos();
            return listTiposSueldos;
        } catch (Exception e) {
            System.out.println("Error tiposSueldos AdmiVigenciasSueldos");
            return null;
        }
    }

    @Override
    public List<MotivosCambiosSueldos> motivosCambiosSueldos() {
        try {
            listMotivosCambiosSueldos = persistenciaMotivosCambiosSueldos.buscarMotivosCambiosSueldos();
            return listMotivosCambiosSueldos;
        } catch (Exception e) {
            System.out.println("Error motivosCambiosSueldos AdmiVigenciasSueldos");
            return null;
        }
    }

    @Override
    public List<TiposEntidades> tiposEntidades() {
        try {
            listTiposEntidades = persistenciaTiposEntidades.buscarTiposEntidades();
            return listTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error tiposEntidades AdmiVigenciasSueldos");
            return null;
        }
    }

    @Override
    public List<Terceros> terceros() {
        try {
            listTerceros = persistenciaTerceros.buscarTerceros();
            return listTerceros;
        } catch (Exception e) {
            System.out.println("Error terceros AdmiVigenciasSueldos");
            return null;
        }
    }
    
    @Override
    public List<TercerosSucursales> tercerosSucursales() {
        try {
            listTercerosSucursales = persistenciaTercerosSucursales.buscarTercerosSucursales();
            return listTercerosSucursales;
        } catch (Exception e) {
            System.out.println("Error tercerosSucursales AdmiVigenciasSueldos");
            return null;
        }
    }
    
    @Override
    public void crearTerceroSurcursal(TercerosSucursales tercerosSucursales) {
        try {
            persistenciaTercerosSucursales.crear(tercerosSucursales);
        } catch (Exception e) {
            System.out.println("Error crearTerceroSucursal AdmiVigenciasSueldos");
        }
    }
    
    @Remove
    @Override
    public void salir() {
        listVigenciasAfiliaciones = null;
        listVigenciasSueldos = null;
    }
}
