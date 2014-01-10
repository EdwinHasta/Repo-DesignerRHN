/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosCesantias;
import InterfaceAdministrar.AdministrarMotivosCesantiasInterface;
import InterfacePersistencia.PersistenciaMotivosCesantiasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosCesantias implements AdministrarMotivosCesantiasInterface {

    @EJB
    PersistenciaMotivosCesantiasInterface persistenciaMotivosCensantias;
    private MotivosCesantias motivosCensantiasSeleccionado;
    private MotivosCesantias motivosCensantias;
    private List<MotivosCesantias> listMotivosCensantias;

    public void modificarMotivosCesantias(List<MotivosCesantias> listaMotivosPrestamosModificados) {
        for (int i = 0; i < listaMotivosPrestamosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivosCensantiasSeleccionado = listaMotivosPrestamosModificados.get(i);
            persistenciaMotivosCensantias.editar(motivosCensantiasSeleccionado);
        }
    }

    public void borrarMotivosCesantias(MotivosCesantias tiposDias) {
        persistenciaMotivosCensantias.borrar(tiposDias);
    }

    public void crearMotivosCesantias(MotivosCesantias tiposDias) {
        persistenciaMotivosCensantias.crear(tiposDias);
    }

    @Override
    public List<MotivosCesantias> mostrarMotivosCesantias() {
        listMotivosCensantias = persistenciaMotivosCensantias.buscarMotivosCesantias();
        return listMotivosCensantias;
    }

    public MotivosCesantias mostrarMotivoCesantia(BigInteger secMotivoPrestamo) {
        motivosCensantias = persistenciaMotivosCensantias.buscarMotivoCensantia(secMotivoPrestamo);
        return motivosCensantias;
    }

    public BigInteger verificarNovedadesSistema(BigInteger secuenciaMotivosCesantias) {
        BigInteger verificarNovedadesSistema = null;
        try {
            verificarNovedadesSistema = persistenciaMotivosCensantias.contadorNovedadesSistema(secuenciaMotivosCesantias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSCESANTIAS verificarNovedadesSistema ERROR :" + e);
        } finally {
            return verificarNovedadesSistema;
        }
    }
}
