/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarClasesPensionesInterface;
import Entidades.ClasesPensiones;
import InterfacePersistencia.PersistenciaClasesPensionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarClasesPensiones implements AdministrarClasesPensionesInterface {

    @EJB
    PersistenciaClasesPensionesInterface persistenciaClasesPensiones;

    @Override
    public void modificarClasesPensiones(List<ClasesPensiones> listaClasesPensiones) {
        for (int i = 0; i < listaClasesPensiones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaClasesPensiones.editar(listaClasesPensiones.get(i));
        }
    }

    @Override
    public void borrarClasesPensiones(List<ClasesPensiones> listaClasesPensiones) {
        for (int i = 0; i < listaClasesPensiones.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaClasesPensiones.borrar(listaClasesPensiones.get(i));
        }
    }

    @Override
    public void crearClasesPensiones(List<ClasesPensiones> listaClasesPensiones) {
        for (int i = 0; i < listaClasesPensiones.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaClasesPensiones.crear(listaClasesPensiones.get(i));
        }
    }

    public List<ClasesPensiones> consultarClasesPensiones() {
        List<ClasesPensiones> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaClasesPensiones.consultarClasesPensiones();
        return listMotivosCambiosCargos;
    }

    @Override
    public ClasesPensiones consultarClasePension(BigInteger secClasesPensiones) {
        ClasesPensiones subCategoria;
        subCategoria = persistenciaClasesPensiones.consultarClasePension(secClasesPensiones);
        return subCategoria;
    }

    @Override
    public BigInteger contarRetiradosClasePension(BigInteger secClasesPensiones) {
        BigInteger contarRetiradosClasePension = null;

        try {
            return contarRetiradosClasePension = persistenciaClasesPensiones.contarRetiradosClasePension(secClasesPensiones);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesPensiones contarEscalafones ERROR : " + e);
            return null;
        }
    }
}
