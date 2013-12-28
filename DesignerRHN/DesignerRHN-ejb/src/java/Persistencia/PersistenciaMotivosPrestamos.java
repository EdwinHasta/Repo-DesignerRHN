/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import InterfacePersistencia.PersistenciaMotivosPrestamosInterface;
import Entidades.MotivosPrestamos;
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
public class PersistenciaMotivosPrestamos implements PersistenciaMotivosPrestamosInterface {

   /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     public void crear(MotivosPrestamos motivosPrestamos) {
        em.persist(motivosPrestamos);
    }

    public void editar(MotivosPrestamos motivosPrestamos) {
        em.merge(motivosPrestamos);
    }

    public void borrar(MotivosPrestamos motivosPrestamos) {
        try {
            em.remove(em.merge(motivosPrestamos));
        } catch (Exception e) {
            System.err.println("Error borrando TiposDias");
            System.out.println(e);
        }
    }

    public MotivosPrestamos buscarMotivoPrestamo(BigInteger secuenciaMP) {
        try {
            return em.find(MotivosPrestamos.class, secuenciaMP);
        } catch (Exception e) {
            return null;
        }
    }

    public List<MotivosPrestamos> buscarMotivosPrestamos() {
        Query query = em.createQuery("SELECT m FROM MotivosPrestamos m ORDER BY m.codigo ASC");
        List<MotivosPrestamos> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    public BigInteger contadorEersPrestamos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM eersprestamos eer , motivosprestamos mp WHERE eer.motivoprestamo = mp.secuencia AND mp.secuencia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSPRESTAMOS CONTADOREERSPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSPRESTAMOS CONTADOREERSPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
