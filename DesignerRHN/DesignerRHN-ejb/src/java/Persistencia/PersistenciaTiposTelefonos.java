/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposTelefonos;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class PersistenciaTiposTelefonos implements PersistenciaTiposTelefonosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear TiposTelefonos.
     */
    @Override
    public void crear(TiposTelefonos tiposTelefonos) {
        try {
            em.persist(tiposTelefonos);
        } catch (Exception e) {
            System.out.println("Error almacenar en persistencia tipos telefonos");
        }
    }

    /*
     *Editar TiposTelefono. 
     */
    @Override
    public void editar(TiposTelefonos tiposTelefonos) {
        em.merge(tiposTelefonos);
    }

    /*
     *Borrar TiposTelefono.
     */
    @Override
    public void borrar(TiposTelefonos tiposTelefonos) {
        try {
            em.remove(em.merge(tiposTelefonos));
        } catch (Exception e) {
            System.out.println("Error TiposTelefonos.borrar");
        }
    }

    /*
     *Encontrar un TipoTelefono. 
     */
    @Override
    public TiposTelefonos buscarTipoTelefonos(BigInteger id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(TiposTelefonos.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<TiposTelefonos> tiposTelefonos() {
        try {
            Query query = em.createQuery("SELECT tt FROM TiposTelefonos tt ORDER BY tt.codigo ASC");
            List<TiposTelefonos> listaTiposTelefonos = query.getResultList();
            return listaTiposTelefonos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.tiposTelefonos" + e);
            return null;
        }
    }
}
