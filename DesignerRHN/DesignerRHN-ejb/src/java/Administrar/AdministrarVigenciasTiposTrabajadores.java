package Administrar;

import Entidades.ClasesPensiones;
import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.Pensionados;
import Entidades.Personas;
import Entidades.Retirados;
import Entidades.TiposPensionados;
import Entidades.TiposTrabajadores;
import Entidades.VigenciasTiposTrabajadores;
import InterfaceAdministrar.AdministrarVigenciasTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaClasesPensionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import InterfacePersistencia.PersistenciaPensionadosInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaRetiradosInterface;
import InterfacePersistencia.PersistenciaTiposPensionadosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaVigenciasTiposTrabajadoresInterface;
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
public class AdministrarVigenciasTiposTrabajadores implements AdministrarVigenciasTiposTrabajadoresInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    @EJB
    PersistenciaVigenciasTiposTrabajadoresInterface persistenciaVigenciasTiposTrabajadores;
    @EJB
    PersistenciaMotivosRetirosInterface persistenciaMotivosRetiros;
    @EJB
    PersistenciaRetiradosInterface persistenciaRetirados;
    @EJB
    PersistenciaPensionadosInterface persistenciaPensionados;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaTiposPensionadosInterface persistenciaTiposPensionados;
    @EJB
    PersistenciaClasesPensionesInterface persistenciaClasesPensiones;
    List<VigenciasTiposTrabajadores> vigenciasTiposTrabajadores;
    VigenciasTiposTrabajadores vigenciaTipoTrabajador;
    Empleados empleado;
    List<TiposTrabajadores> tiposTrabajadores;
    TiposTrabajadores tipoTrabajadorCodigo;
    
    List<MotivosRetiros> motivosRetiros;
    MotivosRetiros motivoRetiroCodigo;
    List<Retirados> retiradosEmpleado;
    Retirados retiradoVigencia;
    
    List<Pensionados> listaPensionados;
    List<Pensionados> listaPensionesEmpleado;
    Pensionados pensionVigencia;
    
    List<Personas> listaPersonas;
    Personas persona;
    
    List<TiposPensionados> tiposPensionados;
    TiposPensionados tipoPension;
    
    List<ClasesPensiones> clasesPensiones;
    ClasesPensiones clasePension;

    public List<VigenciasTiposTrabajadores> vigenciasTiposTrabajadoresEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasTiposTrabajadores = persistenciaVigenciasTiposTrabajadores.buscarVigenciasTiposTrabajadoresEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Tipos Trabajadores (vigenciasTiposTrabajadoresEmpleado)");
            vigenciasTiposTrabajadores = null;
        }
        return vigenciasTiposTrabajadores;
    }

    @Override
    public void modificarVTT(List<VigenciasTiposTrabajadores> listVTTModificadas) {
        for (int i = 0; i < listVTTModificadas.size(); i++) {
            System.out.println("Modificando...");
            vigenciaTipoTrabajador = listVTTModificadas.get(i);
            persistenciaVigenciasTiposTrabajadores.editar(vigenciaTipoTrabajador);
        }
    }

    @Override
    public void borrarVTT(VigenciasTiposTrabajadores vigenciasTiposTrabajadores) {
        persistenciaVigenciasTiposTrabajadores.borrar(vigenciasTiposTrabajadores);
    }

    @Override
    public void crearVTT(VigenciasTiposTrabajadores vigenciasTiposTrabajadores) {
        try {
            persistenciaVigenciasTiposTrabajadores.crear(vigenciasTiposTrabajadores);
            System.out.println("Llamo a persistencia tipo trab");
        } catch (Exception e) {
            System.out.println("Error crearVTT AdministrarVIgenciasTipoTrabajador");
        }
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<TiposTrabajadores> tiposTrabajadores() {
        try {
            tiposTrabajadores = persistenciaTiposTrabajadores.buscarTiposTrabajadores();
            return tiposTrabajadores;
        } catch (Exception e) {
            return null;
        }
    }

    public TiposTrabajadores tipoTrabajadorCodigo(BigDecimal codTipoTrabajador) {
        try {
            tipoTrabajadorCodigo = persistenciaTiposTrabajadores.buscarTipoTrabajadorCodigo(codTipoTrabajador);
            return tipoTrabajadorCodigo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void crearRetirado(Retirados retirado) {
        persistenciaRetirados.crear(retirado);
    }

    @Override
    public void editarRetirado(Retirados retirado) {
        try {
            persistenciaRetirados.editar(retirado);
        } catch (Exception e) {
            System.out.println("Error editarRetirado !!");
        }
    }

    @Override
    public void borrarRetirado(Retirados retirado) {
        persistenciaRetirados.borrar(retirado);
    }

    @Override
    public List<Retirados> retiradosEmpleado(BigInteger secEmpleado) {
        try {
            retiradosEmpleado = persistenciaRetirados.buscarRetirosEmpleado(secEmpleado);
            return retiradosEmpleado;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Retirados retiroPorSecuenciaVigencia(BigInteger secVigencia) {
        try {
            retiradoVigencia = persistenciaRetirados.buscarRetiroVigenciaSecuencia(secVigencia);
            return retiradoVigencia;
        } catch (Exception e) {
            return new Retirados();
        }
    }

    @Override
    public List<MotivosRetiros> motivosRetiros() {
        try {
            motivosRetiros = persistenciaMotivosRetiros.consultarMotivosRetiros();
            return motivosRetiros;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MotivosRetiros motivoRetiroCodigo(BigInteger codMotivoRetiro) {
        try {
            motivoRetiroCodigo = persistenciaMotivosRetiros.consultarMotivoRetiro(codMotivoRetiro);
            return motivoRetiroCodigo;
        } catch (Exception e) {
            return null;
        }
    }
    
    
/////
        
    @Override
    public List<TiposPensionados> tiposPensionados() {
        try {
            tiposPensionados = persistenciaTiposPensionados.consultarTiposPensionados();
            return tiposPensionados;
        } catch (Exception e) {
            System.out.println("error tipospensionado administrarvigenciastipostrabajadores");
            return null;
        }
    }
    
    @Override
    public List<ClasesPensiones> clasesPensiones() {
        try {
            clasesPensiones = persistenciaClasesPensiones.consultarClasesPensiones();
            return clasesPensiones;
        } catch (Exception e) {
            System.out.println("error clasesPensiones administrarvigenciastipostrabajadores");
            return null;
        }
    }

    @Override
    public ClasesPensiones clasePensionCodigo(BigInteger codClasePension) {
        try {
            clasePension = persistenciaClasesPensiones.consultarClasePension(codClasePension);
            return clasePension;
        } catch (Exception e) {
            System.out.println("Error AdministrarVigenciaTipoTrabajador clasePensionCodigo");
            return null;
        }
    }
    
    
    @Override
    public List<Personas> listaPersonas(){
        try{
            listaPersonas = persistenciaPersonas.buscarPersonas();
            return listaPersonas;
        }catch(Exception e){
            System.out.println("Error listaPersonas AdministrarVigenciaTipoTrabajador");
            return null;
        }
    }
    
    @Override
    public Personas personaSecuencia(BigInteger secPersona){
        try{
            persona = persistenciaPersonas.buscarPersonaSecuencia(secPersona);
            return persona;
        }catch(Exception e){
            System.out.println("Error personasSecuencia AdministrarVigenciaTipoTrabajador");
            return null;
        }
    }
    
    
        
    @Override
    public void crearPensionado(Pensionados pension) {
        try{
        persistenciaPensionados.crear(pension);
        }catch(Exception e){
            System.out.println("Error crearPensionado AdministrarVigenciaTiposTrabajadores");
        }
    }

    
    @Override
    public void editarPensionado(Pensionados pension) {
        try {
            persistenciaPensionados.editar(pension);
        } catch (Exception e) {
            System.out.println("Error editarPensionado AdministrarVigenciaTiposTrabajadores");
        }
    }

    
    @Override
    public void borrarPensionado(Pensionados pension) {
        try{
        persistenciaPensionados.borrar(pension);
        }catch(Exception e){
            System.out.println("Error borrarPensionado AdministrarVigenciaTiposTrabajadores");
        }
    }

    
    @Override
    public List<Pensionados> pensionadoEmpleado(BigInteger secEmpleado) {
        try {
            listaPensionesEmpleado = persistenciaPensionados.buscarPensionadosEmpleado(secEmpleado);
            return listaPensionesEmpleado;
        } catch (Exception e) {
            System.out.println("Error pensionadoEmpleado AdministrarVigenciaTiposTrabajadores");
            return null;
        }
    }
    
    @Override
    public List<Pensionados> listaPensionados() {
        try {
            listaPensionados = persistenciaPensionados.buscarPensionados();
            return listaPensionados;
        } catch (Exception e) {
            System.out.println("Error listaPensionados AdministrarVigenciaTiposTrabajadores");
            return null;
        }
    }
    
    @Override
    public Pensionados pensionPorSecuenciaVigencia(BigInteger secVigencia) {
        try {
            pensionVigencia = persistenciaPensionados.buscarPensionVigenciaSecuencia(secVigencia);
            return pensionVigencia;
        } catch (Exception e) {
            System.out.println("Error pensionPorSecuenciaVigencia AdministrarVigenciaTiposTrabajadores");
            return new Pensionados();
        }
    }

    @Remove
    @Override
    public void salir() {
        vigenciaTipoTrabajador = null;
    }
}
