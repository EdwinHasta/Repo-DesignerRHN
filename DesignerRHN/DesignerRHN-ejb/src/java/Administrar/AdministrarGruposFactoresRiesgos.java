/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.GruposFactoresRiesgos;
import InterfaceAdministrar.AdministrarGruposFactoresRiesgosInterface;
import InterfacePersistencia.PersistenciaGruposFactoresRiesgosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarGruposFactoresRiesgos implements AdministrarGruposFactoresRiesgosInterface {

     @EJB
    PersistenciaGruposFactoresRiesgosInterface persistenciaGruposFactoresRiesgos;

    public void modificarGruposFactoresRiesgos(List<GruposFactoresRiesgos> listaGruposFactoresRiesgos) {
        for (int i = 0; i < listaGruposFactoresRiesgos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaGruposFactoresRiesgos.editar(listaGruposFactoresRiesgos.get(i));
        }
    }

    public void borrarGruposFactoresRiesgos(List<GruposFactoresRiesgos> listaGruposFactoresRiesgos) {
        for (int i = 0; i < listaGruposFactoresRiesgos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaGruposFactoresRiesgos.borrar(listaGruposFactoresRiesgos.get(i));
        }
    }

    public void crearGruposFactoresRiesgos(List<GruposFactoresRiesgos> listaGruposFactoresRiesgos) {
        for (int i = 0; i < listaGruposFactoresRiesgos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaGruposFactoresRiesgos.crear(listaGruposFactoresRiesgos.get(i));
        }
    }

     @Override
    public List<GruposFactoresRiesgos> consultarGruposFactoresRiesgos() {
        List<GruposFactoresRiesgos> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaGruposFactoresRiesgos.consultarGruposFactoresRiesgos();
        return listMotivosCambiosCargos;
    }

    public GruposFactoresRiesgos consultarGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos) {
        GruposFactoresRiesgos subCategoria;
        subCategoria = persistenciaGruposFactoresRiesgos.consultarGrupoFactorRiesgo(secGruposFactoresRiesgos);
        return subCategoria;
    }

    public BigInteger contarFactoresRiesgoGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos) {
        BigInteger contarFactoresRiesgoGrupoFactorRiesgo = null;

        try {
            return contarFactoresRiesgoGrupoFactorRiesgo = persistenciaGruposFactoresRiesgos.contarFactoresRiesgoGrupoFactorRiesgo(secGruposFactoresRiesgos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposFactoresRiesgos contarFactoresRiesgoGrupoFactorRiesgo ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarSoIndicadoresGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos) {
        BigInteger contarSoIndicadoresGrupoFactorRiesgo = null;

        try {
            return contarSoIndicadoresGrupoFactorRiesgo = persistenciaGruposFactoresRiesgos.contarSoIndicadoresGrupoFactorRiesgo(secGruposFactoresRiesgos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposFactoresRiesgos contarSoIndicadoresGrupoFactorRiesgo ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarSoProActividadesGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos) {
        BigInteger contarSoProActividadesGrupoFactorRiesgo = null;

        try {
            return contarSoProActividadesGrupoFactorRiesgo = persistenciaGruposFactoresRiesgos.contarSoProActividadesGrupoFactorRiesgo(secGruposFactoresRiesgos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposFactoresRiesgos contarSoProActividadesGrupoFactorRiesgo ERROR : " + e);
            return null;
        }
    }
}
