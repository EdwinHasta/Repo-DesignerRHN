/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposCursosInterface;
import Entidades.TiposCursos;
import InterfacePersistencia.PersistenciaTiposCursosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposCursos implements AdministrarTiposCursosInterface {

    @EJB
    PersistenciaTiposCursosInterface persistenciaTiposCursos;

    @Override
    public void modificarTiposCursos(List<TiposCursos> listaTiposCursos) {
        for (int i = 0; i < listaTiposCursos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposCursos.editar(listaTiposCursos.get(i));
        }
    }

    @Override
    public void borrarTiposCursos(List<TiposCursos> listaTiposCursos) {
        for (int i = 0; i < listaTiposCursos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposCursos.borrar(listaTiposCursos.get(i));
        }
    }

    @Override
    public void crearTiposCursos(List<TiposCursos> listaTiposCursos) {
        for (int i = 0; i < listaTiposCursos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposCursos.crear(listaTiposCursos.get(i));
        }
    }

    @Override
    public List<TiposCursos> consultarTiposCursos() {
        List<TiposCursos> listaTiposCursos;
        listaTiposCursos = persistenciaTiposCursos.consultarTiposCursos();
        return listaTiposCursos;
    }

    @Override
    public TiposCursos consultarTipoIndicador(BigInteger secMotivoDemanda) {
        TiposCursos tiposIndicadores;
        tiposIndicadores = persistenciaTiposCursos.consultarTipoCurso(secMotivoDemanda);
        return tiposIndicadores;
    }

    @Override
    public BigInteger contarCursosTipoCurso(BigInteger secuenciaVigenciasIndicadores) {
        BigInteger contarCursosTipoCurso = null;

        try {
            System.err.println("Secuencia Vigencias Indicadores " + secuenciaVigenciasIndicadores);
            contarCursosTipoCurso = persistenciaTiposCursos.contarCursosTipoCurso(secuenciaVigenciasIndicadores);
        } catch (Exception e) {
            System.err.println("ERROR AdmnistrarTiposCursos contarCursosTipoCurso ERROR :" + e);
        } finally {
            return contarCursosTipoCurso;
        }
    }
}
