/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MetodosPagos;
import InterfaceAdministrar.AdministrarMetodosPagosInterface;
import InterfacePersistencia.PersistenciaMetodosPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarMetodosPagos implements AdministrarMetodosPagosInterface {

    @EJB
    PersistenciaMetodosPagosInterface persistenciaMetodosPagos;

    @Override
    public void modificarMetodosPagos(List<MetodosPagos> listaMetodosPagos) {
        for (int i = 0; i < listaMetodosPagos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMetodosPagos.editar(listaMetodosPagos.get(i));
        }
    }

    @Override
    public void borrarMetodosPagos(List<MetodosPagos> listaMetodosPagos) {
        for (int i = 0; i < listaMetodosPagos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMetodosPagos.borrar(listaMetodosPagos.get(i));
        }
    }

    @Override
    public void crearMetodosPagos(List<MetodosPagos> listaMetodosPagos) {
        for (int i = 0; i < listaMetodosPagos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaMetodosPagos.crear(listaMetodosPagos.get(i));
        }
    }

    @Override
    public List<MetodosPagos> consultarMetodosPagos() {
        List<MetodosPagos> listMetodosPagos;
        listMetodosPagos = persistenciaMetodosPagos.buscarMetodosPagos();
        return listMetodosPagos;
    }

    @Override
    public MetodosPagos consultarMetodoPago(BigInteger secMetodosPagos) {
        MetodosPagos metodosPago;
        metodosPago = persistenciaMetodosPagos.buscarMetodosPagosEmpleado(secMetodosPagos);
        return metodosPago;
    }

    @Override
    public BigInteger verificarMetodosPagosVigenciasFormasPagos(BigInteger secuenciaMetodoPago) {
        BigInteger verificarVigenciasFormasPagos = null;
        try {
            verificarVigenciasFormasPagos = persistenciaMetodosPagos.contadorvigenciasformaspagos(secuenciaMetodoPago);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMETODOSPAGOS VERIFICARVIGENCIASFORMASPAGOS ERROR " + e);
        } finally {
            return verificarVigenciasFormasPagos;
        }
    }
}
