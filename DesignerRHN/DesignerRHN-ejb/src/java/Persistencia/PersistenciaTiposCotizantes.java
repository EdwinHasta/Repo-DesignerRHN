package Persistencia;

import Entidades.Contratos;
import Entidades.TiposCotizantes;
import InterfacePersistencia.PersistenciaTiposCotizantesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaTiposCotizantes implements PersistenciaTiposCotizantesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<TiposCotizantes> lovTiposCotizantes() {
        try {
            Query query = em.createQuery("SELECT tc FROM TiposCotizantes tc ORDER BY tc.codigo ASC");
            List<TiposCotizantes> listaTiposCotizantes = query.getResultList();
            return listaTiposCotizantes;
        } catch (Exception e) {
            return null;
        }
    }
}
