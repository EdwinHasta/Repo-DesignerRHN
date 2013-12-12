/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.GruposInfAdicionales;
import InterfacePersistencia.PersistenciaGruposInfAdicionalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'GruposInfAdicionales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaGruposInfAdicionales implements PersistenciaGruposInfAdicionalesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(GruposInfAdicionales gruposInfAdicionales) { 
        try{
        em.persist(gruposInfAdicionales);
        } catch(Exception e){
            System.out.println("Error creando GruposInfAdicionales PersistenciaGruposInfAdicionales");
        }
    }

    @Override
    public void editar(GruposInfAdicionales gruposInfAdicionales) {
        try {
        em.merge(gruposInfAdicionales);
        } catch(Exception e){
            System.out.println("Error editando GruposInfAdicionales PersistenciaGruposInfAdicionales");
        }
    }

    @Override
    public void borrar(GruposInfAdicionales gruposInfAdicionales) {
        try{
        em.remove(em.merge(gruposInfAdicionales));
        } catch(Exception e){
            System.out.println("Error borrando GruposInfAdicionales PersistenciaGruposInfAdicionales");
        }
    }
    
    @Override
    public GruposInfAdicionales buscarGrupoInfAdicional(BigInteger secuencia) {
        try {
            return em.find(GruposInfAdicionales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarGrupoInfAdicional PersistenciaGruposInfAdicionales : "+e.toString());
            return null;
        }
    }

    @Override
    public List<GruposInfAdicionales> buscarGruposInfAdicionales() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(GruposInfAdicionales.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarGruposInfAdicionales PersistenciaGruposInfAdicionales");
            return null;
        }
    }

}
