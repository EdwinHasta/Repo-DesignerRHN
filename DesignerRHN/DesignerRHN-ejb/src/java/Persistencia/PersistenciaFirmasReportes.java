/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.FirmasReportes;
import InterfacePersistencia.PersistenciaFirmasReportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaFirmasReportes implements PersistenciaFirmasReportesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(FirmasReportes tiposCursos) {
        try {
            System.out.println("PERSISTENCIA CREAR------------------------");
            System.out.println("CODIGO : " + tiposCursos.getCodigo());
            System.out.println("NOMBRE: " + tiposCursos.getDescripcion());
            System.out.println("EMPRESA: " + tiposCursos.getEmpresa().getNombre());
            System.out.println("SUBTITULO : " + tiposCursos.getSubtitulofirma());
            System.out.println("PERSONA : " + tiposCursos.getPersonaFirma().getNombre());
            System.out.println("CARGO : " + tiposCursos.getCargo().getNombre());
            System.out.println("--------------CREAR------------------------");
            em.persist(tiposCursos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaFirmasReportes : " + e.toString());
        }
    }

    public void editar(FirmasReportes tiposCursos) {
        try {
            em.merge(tiposCursos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaFirmasReportes : " + e.toString());
        }
    }

    public void borrar(FirmasReportes tiposCursos) {
        try {
            em.remove(em.merge(tiposCursos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaFirmasReportes : " + e.toString());
        }
    }

    public List<FirmasReportes> consultarFirmasReportes() {
        try {
            Query query = em.createQuery("SELECT g FROM FirmasReportes g ORDER BY g.codigo ASC ");
            List< FirmasReportes> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;

        } catch (Exception e) {
            System.out.println("Error consultarFirmasReportes PersistenciaFirmasReportes : " + e.toString());
            return null;
        }
    }

    public FirmasReportes consultarFirmaReporte(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM FirmasReportes te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            FirmasReportes tiposCursos = (FirmasReportes) query.getSingleResult();
            return tiposCursos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaFirmasReportes consultarTipoCurso : " + e.toString());
            FirmasReportes tiposEntidades = null;
            return tiposEntidades;
        }
    }
}
