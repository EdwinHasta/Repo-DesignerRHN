/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.UbicacionesGeograficas;
import Entidades.VigenciasUbicaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarVigenciasUbicacionesInterface {

    public List<VigenciasUbicaciones> vigenciasUbicacionesEmpleado(BigInteger secEmpleado);

    public void modificarVU(List<VigenciasUbicaciones> listVUModificadas);

    public void borrarVU(VigenciasUbicaciones vigenciasUbicaciones);

    public void crearVU(VigenciasUbicaciones vigenciasUbicaciones);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<UbicacionesGeograficas> ubicacionesGeograficas();
}
