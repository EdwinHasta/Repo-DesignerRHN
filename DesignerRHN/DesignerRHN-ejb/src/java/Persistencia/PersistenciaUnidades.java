package Persistencia;

import Entidades.Unidades;
import InterfacePersistencia.PersistenciaUnidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaUnidades implements PersistenciaUnidadesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Unidades unidad) {
        try {
            em.persist(unidad);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaUnidades.crear");
        }
    }

    @Override
    public void editar(Unidades unidad) {
        try {
            em.merge(unidad);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaUnidades.editar");
        }
    }

    @Override
    public void borrar(Unidades unidad) {
        try {
            em.remove(em.merge(unidad));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaUnidades.borrar");
        }
    }

    @Override
    public List<Unidades> lovUnidades() {
        try {
            Query query = em.createQuery("SELECT u FROM Unidades u ORDER BY u.codigo ASC");
            List<Unidades> listaUnidades = query.getResultList();
            return listaUnidades;
        } catch (Exception e) {
            return null;
        }
    }
}
