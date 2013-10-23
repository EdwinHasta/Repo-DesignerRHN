/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.FormasPagos;
import Entidades.MetodosPagos;
import Entidades.Periodicidades;
import Entidades.Sucursales;
import Entidades.VigenciasFormasPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEmplVigenciasFormasPagosInterface {

    public List<VigenciasFormasPagos> vigenciasFormasPagosPorEmplelado(BigInteger secEmpleado);

    public void modificarVigenciasFormasPagos(List<VigenciasFormasPagos> listVigenciasFormasPagosModificadas);

    public void borrarVigenciasFormasPagos(VigenciasFormasPagos vigenciasFormasPagos);

    public void crearVigencasFormasPagos(VigenciasFormasPagos vigenciasFormasPagos);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<Sucursales> buscarSucursales();

    public List<FormasPagos> buscarFormasPagos();

    public List<MetodosPagos> buscarMetodosPagos();

    public List<Periodicidades> buscarPerdiocidades();
}
