/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.PryClientes;
import InterfaceAdministrar.AdministrarPryClientesInterface;
import InterfacePersistencia.PersistenciaPryClientesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarPryClientes implements AdministrarPryClientesInterface {

  @EJB
    PersistenciaPryClientesInterface persistenciaPryClientes;
    private PryClientes pryClientesSeleccionado;
    private PryClientes pryClientes;
    private List<PryClientes> listPryClientes;

    public void modificarPryClientes(List<PryClientes> listPryClientesModificadas) {
        for (int i = 0; i < listPryClientesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            pryClientesSeleccionado = listPryClientesModificadas.get(i);
            persistenciaPryClientes.editar(pryClientesSeleccionado);
        }
    }

    public void borrarPryClientes(PryClientes pryClientes) {
        persistenciaPryClientes.borrar(pryClientes);
    }

    public void crearPryClientes(PryClientes pryClientes) {
        persistenciaPryClientes.crear(pryClientes);
    }

    public List<PryClientes> mostrarPryClientes() {
        listPryClientes = persistenciaPryClientes.buscarPryClientes();
        return listPryClientes;
    }

    public PryClientes mostrarPryCliente(BigInteger secPryClientes) {
        pryClientes = persistenciaPryClientes.buscarPryCliente(secPryClientes);
        return pryClientes;
    }

    public BigInteger verificarBorradoProyecto(BigInteger secuenciaProyectos) {
            BigInteger verificadorProyectos;

        try {
            System.err.println("Secuencia Borrado Competencias Cargos" + secuenciaProyectos);
            return verificadorProyectos = persistenciaPryClientes.contadorProyectos(secuenciaProyectos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPryClientes verificarBorradoProyecto ERROR :" + e);
        return null;
        } 
    }
}
