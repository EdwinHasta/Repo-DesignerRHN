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

    public void modificarPryPlataformas(List<PryPlataformas> listPryClientesModificadas) {
        for (int i = 0; i < listPryClientesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            pryPlataformasSeleccionado = listPryClientesModificadas.get(i);
            persistenciaPryPlataformas.editar(pryPlataformasSeleccionado);
        }
    }

    public void borrarPryPlataformas(PryPlataformas pryClientes) {
        persistenciaPryPlataformas.borrar(pryClientes);
    }

    public void crearPryPlataformas(PryPlataformas pryClientes) {
        persistenciaPryPlataformas.crear(pryClientes);
    }

    public List<PryPlataformas> mostrarPryPlataformas() {
        listPryPlataformas = persistenciaPryPlataformas.buscarPryPlataformas();
        return listPryPlataformas;
    }

    public PryPlataformas mostrarPryPlataformas(BigInteger secPryClientes) {
        pryPlataformas = persistenciaPryPlataformas.buscarPryPlataformaSecuencia(secPryClientes);
        return pryPlataformas;
    }

    public BigInteger verificarBorradoProyecto(BigInteger secuenciaProyectos) {
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
