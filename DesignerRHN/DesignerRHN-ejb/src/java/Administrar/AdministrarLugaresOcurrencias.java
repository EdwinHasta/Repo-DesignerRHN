/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.LugaresOcurrencias;
import InterfaceAdministrar.AdministrarLugaresOcurrenciasInterface;
import InterfacePersistencia.PersistenciaLugaresOcurrenciasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarLugaresOcurrencias implements AdministrarLugaresOcurrenciasInterface {

  @EJB
    PersistenciaLugaresOcurrenciasInterface persistenciaLugaresOcurrencias;
    private LugaresOcurrencias lugarOcurrenciaSeleccionada;
    private LugaresOcurrencias lugarOcurrencia;
    private List<LugaresOcurrencias> listLugarOcurrencia;

  @Override
    public void modificarLesiones(List<LugaresOcurrencias> listaLugaresOcurrencias) {
        for (int i = 0; i < listaLugaresOcurrencias.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaLugaresOcurrencias.editar(listaLugaresOcurrencias.get(i));
        }
    }

  @Override
    public void borrarLugarOcurrencia(List<LugaresOcurrencias> listaLugaresOcurrencias) {
        for (int i = 0; i < listaLugaresOcurrencias.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaLugaresOcurrencias.borrar(listaLugaresOcurrencias.get(i));
        }
    }

  @Override
    public void crearLugarOcurrencia(List<LugaresOcurrencias> listaLugaresOcurrencias) {
        for (int i = 0; i < listaLugaresOcurrencias.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaLugaresOcurrencias.crear(listaLugaresOcurrencias.get(i));
        }
    }

  @Override
    public List<LugaresOcurrencias> consultarLugaresOcurrencias() {
        listLugarOcurrencia = persistenciaLugaresOcurrencias.buscarLugaresOcurrencias();
        return listLugarOcurrencia;
    }

  @Override
    public LugaresOcurrencias consultarLugarOcurrencia(BigInteger secLugarOcurrencia) {
        lugarOcurrencia = persistenciaLugaresOcurrencias.buscarLugaresOcurrencias(secLugarOcurrencia);
        return lugarOcurrencia;
    }

  @Override
    public BigInteger verificarSoAccidentesLugarOcurrencia(BigInteger secuenciaLugaresOcurrencias) {
        BigInteger verificarSoAccidentes;
        try {
            return verificarSoAccidentes = persistenciaLugaresOcurrencias.contadorSoAccidentes(secuenciaLugaresOcurrencias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARLUGARESOCURRENCIAS VERIFICAR SO ACCIDENTES ERROR : " + e);
            return null;
        }
    }
}
