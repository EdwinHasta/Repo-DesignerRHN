package Persistencia;

import Entidades.Inforeportes;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaInforeportes implements PersistenciaInforeportesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Inforeportes inforeportes) {
        try {
            em.merge(inforeportes);
        } catch (Exception e) {
            System.out.println("El Inforeportes no exite o esta reservada por lo cual no puede ser modificada (Inforeportes)");
        }
    }

    @Override
    public void editar(Inforeportes inforeportes) {
        try {
            em.merge(inforeportes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el Inforeportes");
        }
    }

    @Override
    public void borrar(Inforeportes inforeportes) {
        try {
            em.remove(em.merge(inforeportes));
        } catch (Exception e) {
            System.out.println("El Inforeportes no se ha podido eliminar");
        }
    }

    @Override
    public Inforeportes buscarInforeporte(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(Inforeportes.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarInforeporte Persistencia");
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportes() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inforeportes.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarInforeportes");
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuario() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'NOM' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : "+inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }
}
