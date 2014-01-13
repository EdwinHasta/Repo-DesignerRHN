/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposAccidentes;
import InterfaceAdministrar.AdministrarTiposAccidentesInterface;
import InterfacePersistencia.PersistenciaTiposAccidentesInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposAccidentes implements AdministrarTiposAccidentesInterface {

    @EJB
    PersistenciaTiposAccidentesInterface persistenciaTiposAccidentes;
    private TiposAccidentes TipoAccidenteSeleccionada;
    private TiposAccidentes tiposAccidentes;
    private List<TiposAccidentes> listTiposAccidentes;

    public void modificarTiposAccidentes(List<TiposAccidentes> listTiposAccidentesModificada) {
        for (int i = 0; i < listTiposAccidentesModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            TipoAccidenteSeleccionada = listTiposAccidentesModificada.get(i);
            persistenciaTiposAccidentes.editar(TipoAccidenteSeleccionada);
        }
    }

    public void borrarTiposAccidentes(TiposAccidentes tiposAccidentes) {
        persistenciaTiposAccidentes.borrar(tiposAccidentes);
    }

    public void crearTiposAccidentes(TiposAccidentes TiposAccidentes) {
        persistenciaTiposAccidentes.crear(TiposAccidentes);
    }

    public List<TiposAccidentes> mostrarTiposAccidentes() {
        listTiposAccidentes = persistenciaTiposAccidentes.buscarTiposAccidentes();
        return listTiposAccidentes;
    }

    public TiposAccidentes mostrarTiposAccidentes(BigInteger secTiposAccidentes) {
        tiposAccidentes = persistenciaTiposAccidentes.buscarTipoAccidente(secTiposAccidentes);
        return tiposAccidentes;
    }

    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaTiposAccidentes) {
        BigInteger verificarSoAccidentesMedicos;

        try {
            return verificarSoAccidentesMedicos = persistenciaTiposAccidentes.contadorSoAccidentesMedicos(secuenciaTiposAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSACCIDENTES verificarSoAccidentesMedicos ERROR :" + e);
            return null;
        } finally {
        }
    }

    public BigInteger verificarBorradoAccidentes(BigInteger secuenciaTiposAccidentes) {
        BigInteger verificarBorradoAccidentes;
        try {
            return verificarBorradoAccidentes = persistenciaTiposAccidentes.contadorAccidentes(secuenciaTiposAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSACCIDENTES verificarBorradoAccidentes ERROR :" + e);
            return null;
        } finally {
        }
    }
}
