/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvEntrevistas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarHvEntrevistasInterface {

    public void borrarHvEntrevistas(HvEntrevistas hvEntrevistas);

    public void crearHvEntrevistas(HvEntrevistas hvEntrevistas);

    public void modificarHvEntrevistas(List<HvEntrevistas> listHvEntrevistasModificadas);

    public List<HvEntrevistas> MostrarHvEntrevistasPorEmpleado(BigInteger secEmpleado);

    public HvEntrevistas mostrarHvEntrevista(BigInteger secHvEntrevista);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<HVHojasDeVida> buscarHVHojasDeVida(BigInteger secuencia);
}
