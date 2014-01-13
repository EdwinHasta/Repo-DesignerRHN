/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.PryClientes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarPryClientesInterface {

    public void modificarPryClientes(List<PryClientes> listPryClientesModificadas);

    public void borrarPryClientes(PryClientes pryClientes);

    public void crearPryClientes(PryClientes pryClientes);

    public List<PryClientes> mostrarPryClientes();

    public PryClientes mostrarPryCliente(BigInteger secPryClientes);

    public BigInteger verificarBorradoProyecto(BigInteger secuenciaProyectos);
}
