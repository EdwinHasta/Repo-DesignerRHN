package Persistencia;

import InterfacePersistencia.PersistenciaCandadosInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaCandados implements PersistenciaCandadosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public boolean permisoLiquidar(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Candados c WHERE c.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: permisoLiquidar " + e);
            return false;
        }
    }

    public void liquidar() {
        int i = -100;
        try {
            String sqlQuery = "call PRCUTL_FORMSLIQUIDAR()";
            Query query = em.createNativeQuery(sqlQuery);
            i = query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error liquidar. " + e);
        }
    }

    @Override
    public String estadoLiquidacion(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT c.estado FROM Candados c WHERE c.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            String estadoLiquidacion = (String) query.getSingleResult();
            return estadoLiquidacion;
        } catch (Exception e) {
            System.out.println("Exepcion: estadoLiquidacion " + e);
            return null;
        }
    }
}
