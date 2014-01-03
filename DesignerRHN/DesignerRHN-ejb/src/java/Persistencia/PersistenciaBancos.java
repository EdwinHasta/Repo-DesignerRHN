/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Bancos;
import InterfacePersistencia.PersistenciaBancosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Bancos' de la base de datos
 * @author Andrés Pineda
 */
@Stateless
public class PersistenciaBancos implements PersistenciaBancosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Bancos bancos) {
        try{
        em.persist(bancos);
        } catch(Exception e){
            System.out.println("Error creando bancos persistencia bancos");
        }
    }

    @Override
    public void editar(Bancos bancos) {
        try {
        em.merge(bancos);
        } catch(Exception e){
            System.out.println("Error editando bancos persistencia bancos");
        }
    }

    @Override
    public void borrar(Bancos bancos) {
        try{
        em.remove(em.merge(bancos));
        } catch(Exception e){
            System.out.println("Error borrando bancos persistencia bancos");
        }
    }

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
