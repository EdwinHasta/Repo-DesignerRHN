/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposIndicadoresInterface;
import Entidades.TiposIndicadores;
import InterfacePersistencia.PersistenciaTiposIndicadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposIndicadores implements AdministrarTiposIndicadoresInterface {

    @EJB
    PersistenciaTiposIndicadoresInterface persistenciaTiposIndicadores;
    private TiposIndicadores tiposIndicadorSeleccionado;
    private TiposIndicadores tiposIndicadores;
    private List<TiposIndicadores> listTiposIndicadores;

    public void modificarTiposIndicadores(List<TiposIndicadores> listTiposIndicadoresModificadas) {
        for (int i = 0; i < listTiposIndicadoresModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposIndicadorSeleccionado = listTiposIndicadoresModificadas.get(i);
            persistenciaTiposIndicadores.editar(tiposIndicadorSeleccionado);
        }
    }

    public void borrarTiposIndicadores(TiposIndicadores tiposIndicadores) {
        persistenciaTiposIndicadores.borrar(tiposIndicadores);
    }

    public void crearTiposIndicadores(TiposIndicadores tiposIndicadores) {
        persistenciaTiposIndicadores.crear(tiposIndicadores);
    }

    public List<TiposIndicadores> mostrarTiposIndicadores() {
        listTiposIndicadores = persistenciaTiposIndicadores.buscarTiposIndicadores();
        return listTiposIndicadores;
    }

    public TiposIndicadores mostrarTipoIndicador(BigInteger secMotivoDemanda) {
        tiposIndicadores = persistenciaTiposIndicadores.buscarTiposIndicadoresSecuencia(secMotivoDemanda);
        return tiposIndicadores;
    }

    public BigInteger verificarBorradoVigenciasIndicadores(BigInteger secuenciaVigenciasIndicadores) {
        BigInteger verificadorVigenciasIndicadores = null;

        try {
            System.err.println("Secuencia Vigencias Indicadores " + secuenciaVigenciasIndicadores);
            verificadorVigenciasIndicadores = persistenciaTiposIndicadores.contadorVigenciasIndicadores(secuenciaVigenciasIndicadores);
        } catch (Exception e) {
            System.err.println("ERROR AdmnistrarTiposIndicadores verificarBorradoVigenciasIndicadores ERROR :" + e);
        } finally {
            return verificadorVigenciasIndicadores;
        }
    }
}
