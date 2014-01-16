/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.GruposInfAdicionales;
import InterfaceAdministrar.AdministrarGruposInfAdicionalesInterface;
import InterfacePersistencia.PersistenciaGruposInfAdicionalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarGruposInfAdicionales implements AdministrarGruposInfAdicionalesInterface {

    @EJB
    PersistenciaGruposInfAdicionalesInterface persistenciaGruposInfAdicionales;
    private GruposInfAdicionales grupoInfAdicionalSeleccionado;
    private GruposInfAdicionales gruposInfAdicionales;
    private List<GruposInfAdicionales> listGruposInfAdicionales;

    @Override
    public void modificarGruposInfAdicionales(List<GruposInfAdicionales> listGruposInfAdicionales) {
        for (int i = 0; i < listGruposInfAdicionales.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaGruposInfAdicionales.editar(listGruposInfAdicionales.get(i));
        }
    }

    @Override
    public void borrarGruposInfAdicionales(List<GruposInfAdicionales> listGruposInfAdicionales) {
        for (int i = 0; i < listGruposInfAdicionales.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaGruposInfAdicionales.borrar(listGruposInfAdicionales.get(i));
        }
    }

    @Override
    public void crearGruposInfAdicionales(List<GruposInfAdicionales> listGruposInfAdicionales) {
        for (int i = 0; i < listGruposInfAdicionales.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaGruposInfAdicionales.crear(listGruposInfAdicionales.get(i));
        }
    }

    @Override
    public List<GruposInfAdicionales> consultarGruposInfAdicionales() {
        listGruposInfAdicionales = persistenciaGruposInfAdicionales.buscarGruposInfAdicionales();
        return listGruposInfAdicionales;
    }

    @Override
    public GruposInfAdicionales consultarGrupoInfAdicional(BigInteger secDeportes) {
        gruposInfAdicionales = persistenciaGruposInfAdicionales.buscarGrupoInfAdicional(secDeportes);
        return gruposInfAdicionales;
    }

    @Override
    public BigInteger verificarInformacionesAdicionales(BigInteger secuenciaGruposInfAdicionales) {
        BigInteger verificadorInformacionesAdicionales = null;
        try {
            System.err.println("Secuencia Grupo Inf Adicional : " + secuenciaGruposInfAdicionales);
            verificadorInformacionesAdicionales = persistenciaGruposInfAdicionales.contadorInformacionesAdicionales(secuenciaGruposInfAdicionales);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEstadosCiviles VigenciasEstadoCiviles ERROR :" + e);
        } finally {
            return verificadorInformacionesAdicionales;
        }
    }
}
