/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposEmpresas;
import InterfacePersistencia.PersistenciaTiposEmpresasInterface;
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
public class PersistenciaTiposEmpresas implements PersistenciaTiposEmpresasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(TiposEmpresas tiposEmpresas) {
        em.persist(tiposEmpresas);
    }

    public void editar(TiposEmpresas tiposEmpresas) {
        em.merge(tiposEmpresas);
    }

    public void borrar(TiposEmpresas tiposEmpresas) {
        em.remove(em.merge(tiposEmpresas));
    }

    public TiposEmpresas buscarTipoEmpresa(BigInteger secuenciaTE) {
        try {
            return em.find(TiposEmpresas.class, secuenciaTE);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposEmpresas> buscarTiposEmpresas() {
        Query query = em.createQuery("SELECT m FROM TiposEmpresas m ORDER BY m.codigo ASC ");
        List<TiposEmpresas> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigInteger contadorSueldosMercados(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT (*)FROM sueldosmercados sm WHERE tipoempresa = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador TiposEmpresas contadorIdiomasPersonas persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error TiposEmpresas contadorIdiomasPersonas. " + e);
            return retorno;
        }
    }
}
