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
    public void modificarLesiones(List<LugaresOcurrencias> listLugaresOcurrenciasModificadas) {
        for (int i = 0; i < listLugaresOcurrenciasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            lugarOcurrenciaSeleccionada = listLugaresOcurrenciasModificadas.get(i);
            persistenciaLugaresOcurrencias.editar(lugarOcurrenciaSeleccionada);
        }
    }

  @Override
    public void borrarLugarOcurrencia(LugaresOcurrencias lugarOcurrencia) {
        persistenciaLugaresOcurrencias.borrar(lugarOcurrencia);
    }

  @Override
    public void crearLugarOcurrencia(LugaresOcurrencias lugarOcurrencia) {
        persistenciaLugaresOcurrencias.crear(lugarOcurrencia);
    }

  @Override
    public List<LugaresOcurrencias> mostrarLugaresOcurrencias() {
        listLugarOcurrencia = persistenciaLugaresOcurrencias.buscarLugaresOcurrencias();
        return listLugarOcurrencia;
    }

  @Override
    public LugaresOcurrencias mostrarLugarOcurrencia(BigInteger secLugarOcurrencia) {
        lugarOcurrencia = persistenciaLugaresOcurrencias.buscarLugaresOcurrencias(secLugarOcurrencia);
        return lugarOcurrencia;
    }

  @Override
    public BigInteger verificarSoAccidentes(BigInteger secuenciaLugaresOcurrencias) {
        BigInteger verificarSoAccidentes;
        try {
            return verificarSoAccidentes = persistenciaLugaresOcurrencias.contadorSoAccidentes(secuenciaLugaresOcurrencias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARLUGARESOCURRENCIAS VERIFICAR SO ACCIDENTES ERROR : " + e);
            return null;
        }
    }
}
