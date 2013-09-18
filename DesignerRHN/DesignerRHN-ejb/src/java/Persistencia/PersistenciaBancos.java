/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Bancos;
import InterfacePersistencia.PersistenciaBancosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaBancos implements PersistenciaBancosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Bancos.
     */
    @Override
    public void crear(Bancos bancos) {
        try{
        em.persist(bancos);
        } catch(Exception e){
            System.out.println("Error creando bancos persistencia bancos");
        }
    }

    /*
     *Editar Bancos. 
     */
    @Override
    public void editar(Bancos bancos) {
        try {
        em.merge(bancos);
        } catch(Exception e){
            System.out.println("Error editando bancos persistencia bancos");
        }
    }

    /*
     *Borrar Bancos.
     */
    @Override
    public void borrar(Bancos bancos) {
        try{
        em.remove(em.merge(bancos));
        } catch(Exception e){
            System.out.println("Error borrando bancos persistencia bancos");
        }
    }

    /*
     *Encontrar un Bancos. 
     */
    @Override
    public Bancos buscarBanco(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(Bancos.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarbanco persistencia bancos : "+e.toString());
            return null;
        }
    }

    /*
     *Encontrar todos los Bancos.
     */
    @Override
    public List<Bancos> buscarBancos() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Bancos.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarBancos persistencia bancos");
            return null;
        }
    }
}
