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
    private MetodosPagos metodosPagoSeleccionado;
    private MetodosPagos metodosPago;
    private List<MetodosPagos> listMetodosPagos;

    public void modificarMetodosPagos(List<MetodosPagos> listMotivosCambiosCargosModificadas) {
        for (int i = 0; i < listMotivosCambiosCargosModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            metodosPagoSeleccionado = listMotivosCambiosCargosModificadas.get(i);
            persistenciaMetodosPagos.editar(metodosPagoSeleccionado);
        }
    }

    public void borrarMetodosPagos(MetodosPagos metodosPagos) {
        persistenciaMetodosPagos.borrar(metodosPagos);
    }

    public void crearMetodosPagos(MetodosPagos metodosPagos) {
        persistenciaMetodosPagos.crear(metodosPagos);
    }

    public List<MetodosPagos> mostrarMetodosPagos() {
        listMetodosPagos = persistenciaMetodosPagos.buscarMetodosPagos();
        return listMetodosPagos;
    }

    public MetodosPagos mostrarMetodoPago(BigInteger secMetodosPagos) {
        metodosPago = persistenciaMetodosPagos.buscarMetodosPagosEmpleado(secMetodosPagos);
        return metodosPago;
    }

    public BigInteger verificarVigenciasFormasPagos(BigInteger secuenciaMetodoPago) {
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
