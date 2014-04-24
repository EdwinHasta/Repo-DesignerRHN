/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Conceptos;
import Entidades.GruposConceptos;
import Entidades.VigenciasGruposConceptos;
import InterfaceAdministrar.AdministrarGruposConceptosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaGruposConceptosInterface;
import InterfacePersistencia.PersistenciaVigenciasGruposConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarGruposConceptos implements AdministrarGruposConceptosInterface {

    @EJB
    PersistenciaGruposConceptosInterface persistenciaGruposConceptos;
    @EJB
    PersistenciaVigenciasGruposConceptosInterface persistenciaVigenciasGruposConceptos;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;

    @Override
    public List<GruposConceptos> buscarGruposConceptos() {
        try {
            return persistenciaGruposConceptos.buscarGruposConceptos();
        } catch (Exception e) {
            System.err.println("Error AdministrarVigenciasFormales.vigenciasFormalesPersona " + e);
            return null;
        }
    }

    @Override
    public void modificarGruposConceptos(List<GruposConceptos> listaGruposConceptosModificar) {
        for (int i = 0; i < listaGruposConceptosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaGruposConceptosModificar.get(i).getFundamental() == null) {
                listaGruposConceptosModificar.get(i).setFundamental(null);
            }
            persistenciaGruposConceptos.editar(listaGruposConceptosModificar.get(i));
        }
    }

    @Override
    public void borrarGruposConceptos(GruposConceptos gruposConceptos) {
        persistenciaGruposConceptos.borrar(gruposConceptos);
    }

    @Override
    public void crearGruposConceptos(GruposConceptos gruposConceptos) {
        persistenciaGruposConceptos.crear(gruposConceptos);
    }

//Vigencias Grupos Conceptos
    @Override
    public void modificarVigenciaGruposConceptos(List<VigenciasGruposConceptos> listaVigenciasGruposConceptosModificar) {
        for (int i = 0; i < listaVigenciasGruposConceptosModificar.size(); i++) {
            System.out.println("Modificando...");
            persistenciaVigenciasGruposConceptos.editar(listaVigenciasGruposConceptosModificar.get(i));
        }
    }

    @Override
    public void borrarVigenciaGruposConceptos(VigenciasGruposConceptos vigenciasGruposConceptos) {
        persistenciaVigenciasGruposConceptos.borrar(vigenciasGruposConceptos);
    }

    @Override
    public void crearVigenciaGruposConceptos(VigenciasGruposConceptos vigenciasGruposConceptos) {
        persistenciaVigenciasGruposConceptos.crear(vigenciasGruposConceptos);
    }

    @Override
    public List<VigenciasGruposConceptos> buscarVigenciasGruposConceptos(BigInteger secuencia) {
        try {
            return persistenciaVigenciasGruposConceptos.listVigenciasGruposConceptosPorGrupoConcepto(secuencia);
        } catch (Exception e) {
            System.err.println("Error AdministrarVigenciasFormales.vigenciasFormalesPersona " + e);
            return null;
        }
    }

    //LOV DE ABAJO
    public List<Conceptos> lovConceptos() {
        try {
            return persistenciaConceptos.conceptoEmpresa();
        } catch (Exception e) {
            System.err.println("Error AdministrarVigenciasFormales.vigenciasFormalesPersona " + e);
            return null;
        }
    }

}
