/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import Entidades.TiposFamiliares;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarHvReferencias1Interface {
     public void borrarHvReferencias1(HvReferencias hvEntrevistas) ;
    public void crearHvReferencias1(HvReferencias hvEntrevistas);
    public void modificarHvReferencias1(List<HvReferencias> listHvReferenciasModificadas);
    public List<HvReferencias> MostrarHvReferenciasPorEmpleado1(BigInteger secEmpleado);
    public HvReferencias mostrarHvReferencia1(BigInteger secHvEntrevista);
    public Empleados buscarEmpleado(BigInteger secuencia) ;
    public List<HVHojasDeVida> buscarHvHojasDeVida(BigInteger secuencia);
    public List<TiposFamiliares> buscarTiposFamiliares();
}
