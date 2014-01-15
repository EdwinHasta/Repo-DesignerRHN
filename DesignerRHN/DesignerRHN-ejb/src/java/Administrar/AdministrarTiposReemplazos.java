/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposReemplazos;
import InterfaceAdministrar.AdministrarTiposReemplazosInterface;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposReemplazos implements AdministrarTiposReemplazosInterface {

    @EJB
    PersistenciaTiposReemplazosInterface persistenciaTiposReemplazos;
    private TiposReemplazos tiposReemplazoSeleccionado;
    private TiposReemplazos tiposReemplazo;
    private List<TiposReemplazos> listTiposReemplazos;

    @Override
    public void modificarTiposReemplazos(List<TiposReemplazos> listaTiposReemplazosModificados) {
        for (int i = 0; i < listaTiposReemplazosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposReemplazoSeleccionado = listaTiposReemplazosModificados.get(i);
            persistenciaTiposReemplazos.editar(tiposReemplazoSeleccionado);
        }
    }

    @Override
    public void borrarTiposReemplazos(TiposReemplazos tiposIndicadores) {
        persistenciaTiposReemplazos.borrar(tiposIndicadores);
    }

    @Override
    public void crearTiposReemplazos(TiposReemplazos tiposIndicadores) {
        persistenciaTiposReemplazos.crear(tiposIndicadores);
    }

    @Override
    public List<TiposReemplazos> mostrarTiposReemplazos() {
        listTiposReemplazos = persistenciaTiposReemplazos.buscarTiposReemplazos();
        return listTiposReemplazos;
    }

    @Override
    public TiposReemplazos mostrarTipoReemplazo(BigInteger secMotivoDemanda) {
        tiposReemplazo = persistenciaTiposReemplazos.buscarTipoReemplazo(secMotivoDemanda);
        return tiposReemplazo;
    }

    @Override
    public BigInteger verificarBorradoEncargaturas(BigInteger secuenciaTiposReemplazos) {
        BigInteger verificarBorradoEncargaturas = null;
        try {
            System.out.println("Secuencia Vigencias Indicadores " + secuenciaTiposReemplazos);
            return verificarBorradoEncargaturas = persistenciaTiposReemplazos.contadorEncargaturas(secuenciaTiposReemplazos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSREEMPLAZOS VERIFICARBORRADOENCARGATURAS ERROR :" + e);

            return verificarBorradoEncargaturas;
        }
    }

    @Override
    public BigInteger verificarBorradoProgramacionesTiempos(BigInteger secuenciaTiposReemplazos) {
        BigInteger verificarBorradoProgramacionesTiempos = null;
        try {
            System.out.println("Secuencia Vigencias Indicadores " + secuenciaTiposReemplazos);
            return verificarBorradoProgramacionesTiempos = persistenciaTiposReemplazos.contadorProgramacionesTiempos(secuenciaTiposReemplazos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSREEMPLAZOS VERIFICARBORRADOPROGRAMACIONESTIEMPOS ERROR :" + e);

            return verificarBorradoProgramacionesTiempos;
        }
    }

    @Override
    public BigInteger verificarBorradoReemplazos(BigInteger secuenciaTiposReemplazos) {
        BigInteger verificarBorradoReemplazos = null;
        try {
            System.out.println("Secuencia Vigencias Indicadores " + secuenciaTiposReemplazos);
            return verificarBorradoReemplazos = persistenciaTiposReemplazos.contadorReemplazos(secuenciaTiposReemplazos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSREEMPLAZOS VERIFICARBORRADOREEMPLAZOS ERROR :" + e);

            return verificarBorradoReemplazos;
        }
    }

    @Override
    public List<TiposReemplazos> lovTiposReemplazos() {
        return persistenciaTiposReemplazos.buscarTiposReemplazos();
    }
}
