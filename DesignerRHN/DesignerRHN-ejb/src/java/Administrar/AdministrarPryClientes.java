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

    @Override
    public void modificarPryClientes(List<PryClientes> listaPryClientes) {
        for (int i = 0; i < listaPryClientes.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaPryClientes.editar(listaPryClientes.get(i));
        }
    }
    @Override
    public void borrarPryClientes(List<PryClientes> listaPryClientes) {
        for (int i = 0; i < listaPryClientes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaPryClientes.borrar(listaPryClientes.get(i));
        }
    }
    @Override
    public void crearPryClientes(List<PryClientes> listaPryClientes) {
        for (int i = 0; i < listaPryClientes.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaPryClientes.crear(listaPryClientes.get(i));
        }
    }
    @Override
    public List<PryClientes> consultarPryClientes() {
        List<PryClientes> listPryClientes;
        listPryClientes = persistenciaPryClientes.buscarPryClientes();
        return listPryClientes;
    }
    @Override
    public PryClientes consultarPryCliente(BigInteger secPryClientes) {
        PryClientes pryClientes;
        pryClientes = persistenciaPryClientes.buscarPryCliente(secPryClientes);
        return pryClientes;
    }
    @Override
    public BigInteger contarProyectosPryCliente(BigInteger secuenciaProyectos) {
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
