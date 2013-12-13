package Administrar;

import Entidades.CentrosCostos;
import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.MotivosLocalizaciones;
import Entidades.Proyectos;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasProrrateos;
import Entidades.VigenciasProrrateosProyectos;
import InterfaceAdministrar.AdministrarVigenciaLocalizacionInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaMotivosLocalizacionesInterface;
import InterfacePersistencia.PersistenciaProyectosInterface;
import InterfacePersistencia.PersistenciaVigenciasLocalizacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasProrrateosInterface;
import InterfacePersistencia.PersistenciaVigenciasProrrateosProyectosInterface;
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
public class AdministrarVigenciaLocalizacion implements AdministrarVigenciaLocalizacionInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaMotivosLocalizacionesInterface persistenciaMotivosLocalizaciones;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    @EJB
    PersistenciaVigenciasLocalizacionesInterface persistenciaVigenciasLocalizaciones;
    @EJB
    PersistenciaProyectosInterface persistenciaProyectos;
    @EJB
    PersistenciaVigenciasProrrateosInterface persistenciaVigenciasProrrateos;
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    @EJB
    PersistenciaVigenciasProrrateosProyectosInterface persistenciaVigenciasProrrateosProyectos;
    
    //Vigencias Prorrateos
    List<VigenciasProrrateos> vigenciasProrrateos;
    VigenciasProrrateos vigenciaProrrateo;
    //Vigencias Localizaciones
    List<VigenciasLocalizaciones> vigenciasLocalizaciones;
    VigenciasLocalizaciones vigenciaLocalizacion;
    //Vigencias Prorrateos Proyectos
    List<VigenciasProrrateosProyectos> vigenciasProrrateosProyectos;
    VigenciasProrrateosProyectos vigenciaProrrateoProyecto;
    //Empleado
    Empleados empleado;
    //Listas Adicionales
    List<MotivosLocalizaciones> motivosLocalizaciones;
    List<Estructuras> estructuras;
    List<Proyectos> proyectos;
    List<CentrosCostos> centrosCostos;

    @Override
    public List<VigenciasLocalizaciones> VigenciasLocalizacionesEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasLocalizaciones = persistenciaVigenciasLocalizaciones.buscarVigenciasLocalizacionesEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Localizaciones (VigenciasLocalizacionesEmpleado)");
            vigenciasLocalizaciones = null;
        }
        return vigenciasLocalizaciones;
    }

    @Override
    public void modificarVL(List<VigenciasLocalizaciones> listVLModificadas) {
        try {
            for (int i = 0; i < listVLModificadas.size(); i++) {
                System.out.println("Modificando...");
                if (listVLModificadas.get(i).getProyecto().getSecuencia() == null) {
                    listVLModificadas.get(i).setProyecto(null);
                }
                if (listVLModificadas.get(i).getLocalizacion().getSecuencia() == null) {
                    listVLModificadas.get(i).setLocalizacion(null);
                }
                if (listVLModificadas.get(i).getMotivo().getSecuencia() == null) {
                    listVLModificadas.get(i).setLocalizacion(null);
                }
                vigenciaLocalizacion = listVLModificadas.get(i);
                persistenciaVigenciasLocalizaciones.editar(vigenciaLocalizacion);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVL AdmiVigLoc");
        }
    }

    @Override
    public void borrarVL(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            persistenciaVigenciasLocalizaciones.borrar(vigenciasLocalizaciones);
        } catch (Exception e) {
            System.out.println("Error borrarVL AdmiVigLoc");
        }
    }

    @Override
    public void crearVL(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            persistenciaVigenciasLocalizaciones.crear(vigenciasLocalizaciones);
        } catch (Exception e) {
            System.out.println("Error crearVL AdmiVigLoc");
        }
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            System.out.println("Error buscarEmpleado Adm");
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<MotivosLocalizaciones> motivosLocalizaciones() {
        try {
            motivosLocalizaciones = persistenciaMotivosLocalizaciones.buscarMotivosLocalizaciones();
            return motivosLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error motivosLocalizaciones AdministrarVigenciasdLocalizaciones");
            return null;
        }
    }

    @Override
    public List<Estructuras> estructuras() {
        try {
            estructuras = persistenciaEstructuras.buscarEstructuras();
            return estructuras;
        } catch (Exception e) {
            System.out.println("Error estructuras AdministrarVigenciasdLocalizaciones");
            return null;
        }
    }

    @Override
    public List<Proyectos> proyectos() {
        try {
            proyectos = persistenciaProyectos.proyectos();
            return proyectos;
        } catch (Exception e) {
            System.out.println("Error proyectos AdministrarVigenciasdLocalizaciones");
            return null;
        }
    }
    
    @Override
    public List<VigenciasProrrateos> VigenciasProrrateosVigencia(BigInteger secVigencia) {
        try {
            vigenciasProrrateos = persistenciaVigenciasProrrateos.buscarVigenciasProrrateosVigenciaSecuencia(secVigencia);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Localizaciones (VigenciasProrrateosVigencia)");
            vigenciasProrrateos = null;
        }
        return vigenciasProrrateos;
    }
    
    @Override
    public void modificarVP(List<VigenciasProrrateos> listVPModificadas) {
        try {
            for (int i = 0; i < listVPModificadas.size(); i++) {
                System.out.println("Modificando Vigencias Prorrateo...");
                if (listVPModificadas.get(i).getProyecto().getSecuencia() == null) {
                    listVPModificadas.get(i).setProyecto(null);
                }
                if (listVPModificadas.get(i).getCentrocosto().getSecuencia() == null) {
                    listVPModificadas.get(i).setCentrocosto(null);
                }
                vigenciaProrrateo = listVPModificadas.get(i);
                persistenciaVigenciasProrrateos.editar(vigenciaProrrateo);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVP AdmiVigLoc");
        }
    }


    @Override
    public void borrarVP(VigenciasProrrateos vigenciasProrrateos) {
        try {
            persistenciaVigenciasProrrateos.borrar(vigenciasProrrateos);
        } catch (Exception e) {
            System.out.println("Error borrarVP AdmiVigLoc");
        }
    }


    @Override
    public void crearVP(VigenciasProrrateos vigenciasProrrateos) {
        try {
            persistenciaVigenciasProrrateos.crear(vigenciasProrrateos);
        } catch (Exception e) {
            System.out.println("Error crearVP AdmiVigLoc");
        }
    }
    
    @Override
    public  List<CentrosCostos> centrosCostos(){
        try{
            centrosCostos = persistenciaCentrosCostos.buscarCentrosCostos();
            return centrosCostos;
        }catch(Exception e){
            System.out.println("Error centrosCostos Admi");
            return null;
        }
    }
    

    @Override
    public List<VigenciasProrrateosProyectos> VigenciasProrrateosProyectosVigencia(BigInteger secVigencia) {
       try {
            vigenciasProrrateosProyectos = persistenciaVigenciasProrrateosProyectos.buscarVigenciasProrrateosProyectosVigenciaSecuencia(secVigencia);
        } catch (Exception e) {
            vigenciasProrrateosProyectos = null;
        }
        return vigenciasProrrateosProyectos;
    }
    

    @Override
    public void modificarVPP(List<VigenciasProrrateosProyectos> listVPPModificadas) {
        try {
            for (int i = 0; i < listVPPModificadas.size(); i++) {
                System.out.println("Modificando VigenciasProrrateosProyectos...");
                if (listVPPModificadas.get(i).getProyecto().getSecuencia() == null) {
                        listVPPModificadas.get(i).setProyecto(null);
                    }
                vigenciaProrrateoProyecto = listVPPModificadas.get(i);
                persistenciaVigenciasProrrateosProyectos.editar(vigenciaProrrateoProyecto);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVPP AdmiVigLoc");
        }
    }



    @Override
    public void borrarVPP(VigenciasProrrateosProyectos vigenciasProrrateosProyectos) {
        try {
            persistenciaVigenciasProrrateosProyectos.borrar(vigenciasProrrateosProyectos);
        } catch (Exception e) {
            System.out.println("Error borrarVPP AdmiVigLoc");
        }
    }



    @Override
    public void crearVPP(VigenciasProrrateosProyectos vigenciasProrrateosProyectos) {
        try {
            persistenciaVigenciasProrrateosProyectos.crear(vigenciasProrrateosProyectos);
        } catch (Exception e) {
            System.out.println("Error crearVPP AdmiVigLoc");
        }
    }
    
    @Remove
    @Override
    public void salir() {
        vigenciaLocalizacion = null;
    }
}
