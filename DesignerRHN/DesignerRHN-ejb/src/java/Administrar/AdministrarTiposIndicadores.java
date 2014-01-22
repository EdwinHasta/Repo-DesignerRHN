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

    @Override
    public void modificarTiposIndicadores(List<TiposIndicadores> listTiposIndicadores) {
        for (int i = 0; i < listTiposIndicadores.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposIndicadores.editar(listTiposIndicadores.get(i));
        }
    }

    @Override
    public void borrarTiposIndicadores(List<TiposIndicadores> listTiposIndicadores) {
        for (int i = 0; i < listTiposIndicadores.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposIndicadores.borrar(listTiposIndicadores.get(i));
        }
    }

    @Override
    public void crearTiposIndicadores(List<TiposIndicadores> listTiposIndicadores) {
        for (int i = 0; i < listTiposIndicadores.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposIndicadores.crear(listTiposIndicadores.get(i));
        }
    }

    @Override
    public List<TiposIndicadores> consultarTiposIndicadores() {
        List<TiposIndicadores> listTiposIndicadores;
        listTiposIndicadores = persistenciaTiposIndicadores.buscarTiposIndicadores();
        return listTiposIndicadores;
    }

    @Override
    public TiposIndicadores consultarTipoIndicador(BigInteger secMotivoDemanda) {
        TiposIndicadores tiposIndicadores;
        tiposIndicadores = persistenciaTiposIndicadores.buscarTiposIndicadoresSecuencia(secMotivoDemanda);
        return tiposIndicadores;
    }

    @Override
    public BigInteger contarVigenciasIndicadoresTipoIndicador(BigInteger secuenciaVigenciasIndicadores) {
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
