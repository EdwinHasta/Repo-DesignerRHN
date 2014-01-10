/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarHvReferenciasInterface {

    public void borrarHvReferencias(HvReferencias hvEntrevistas);

    public void crearHvReferencias(HvReferencias hvEntrevistas);

    public void modificarHvReferencias(List<HvReferencias> listHvReferenciasModificadas);

    public List<HvReferencias> MostrarHvReferenciasPorEmpleado(BigInteger secEmpleado);

    public HvReferencias mostrarHvReferencia(BigInteger secHvEntrevista);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<HVHojasDeVida> buscarHvHojasDeVida(BigInteger secuencia);
}
