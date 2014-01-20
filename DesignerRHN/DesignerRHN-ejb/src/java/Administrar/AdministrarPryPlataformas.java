/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.PryPlataformas;
import InterfaceAdministrar.AdministrarPryPlataformasInterface;
import InterfacePersistencia.PersistenciaPryPlataformasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarPryPlataformas implements AdministrarPryPlataformasInterface {

    @EJB
    PersistenciaPryPlataformasInterface persistenciaPryPlataformas;
    private PryPlataformas pryPlataformasSeleccionado;
    private PryPlataformas pryPlataformas;
    private List<PryPlataformas> listPryPlataformas;

    @Override
    public void modificarPryPlataformas(List<PryPlataformas> listaPryClientes) {
        for (int i = 0; i < listaPryClientes.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaPryPlataformas.editar(listaPryClientes.get(i));
        }
    }

    @Override
    public void borrarPryPlataformas(List<PryPlataformas> listaPryClientes) {
        for (int i = 0; i < listaPryClientes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaPryPlataformas.borrar(listaPryClientes.get(i));
        }
    }

    @Override
    public void crearPryPlataformas(List<PryPlataformas> listaPryClientes) {
        for (int i = 0; i < listaPryClientes.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaPryPlataformas.crear(listaPryClientes.get(i));
        }
    }
    @Override
    public List<PryPlataformas> mostrarPryPlataformas() {
        listPryPlataformas = persistenciaPryPlataformas.buscarPryPlataformas();
        return listPryPlataformas;
    }
    @Override
    public PryPlataformas mostrarPryPlataformas(BigInteger secPryClientes) {
        pryPlataformas = persistenciaPryPlataformas.buscarPryPlataformaSecuencia(secPryClientes);
        return pryPlataformas;
    }
    @Override
    public BigInteger contarProyectosPryPlataformas(BigInteger secuenciaProyectos) {
        BigInteger verificadorProyectos;
        try {
            System.err.println("Secuencia Borrado Competencias Cargos" + secuenciaProyectos);
            return verificadorProyectos = persistenciaPryPlataformas.contadorProyectos(secuenciaProyectos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPryPlataformas verificarBorradoProyecto ERROR :" + e);
            return null;
        }
    }
}
