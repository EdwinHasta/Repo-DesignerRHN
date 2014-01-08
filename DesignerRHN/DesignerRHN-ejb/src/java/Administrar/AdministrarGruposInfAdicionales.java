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

    public void modificarGruposInfAdicionales(List<GruposInfAdicionales> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            grupoInfAdicionalSeleccionado = listDeportesModificadas.get(i);
            persistenciaGruposInfAdicionales.editar(grupoInfAdicionalSeleccionado);
        }
    }

    public void borrarGruposInfAdicionales(GruposInfAdicionales deportes) {
        persistenciaGruposInfAdicionales.borrar(deportes);
    }

    public void crearGruposInfAdicionales(GruposInfAdicionales deportes) {
        persistenciaGruposInfAdicionales.crear(deportes);
    }

    public List<GruposInfAdicionales> mostrarGruposInfAdicionales() {
        listGruposInfAdicionales = persistenciaGruposInfAdicionales.buscarGruposInfAdicionales();
        return listGruposInfAdicionales;
    }

    public GruposInfAdicionales mostrarGrupoInfAdicional(BigInteger secDeportes) {
        gruposInfAdicionales = persistenciaGruposInfAdicionales.buscarGrupoInfAdicional(secDeportes);
        return gruposInfAdicionales;
    }

    public BigInteger verificadorInformacionesAdicionales(BigInteger secuenciaGruposInfAdicionales) {
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
