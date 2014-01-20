/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Contratos;
import Entidades.TiposCotizantes;
import InterfaceAdministrar.AdministrarContratosInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaTiposCotizantesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Contratos'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarContratos implements AdministrarContratosInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaContratos'.
     */
    @EJB
    PersistenciaContratosInterface persistenciaContratos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaTiposCotizantes'.
     */
    @EJB
    PersistenciaTiposCotizantesInterface persistenciaTiposCotizantes;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Contratos> consultarContratos() {
        return persistenciaContratos.lovContratos();
    }

    @Override
    public List<TiposCotizantes> consultaLOVTiposCotizantes() {
        return persistenciaTiposCotizantes.lovTiposCotizantes();
    }

    @Override
    public void modificarConceptos(List<Contratos> listContratosModificados) {
        for (int i = 0; i < listContratosModificados.size(); i++) {
            System.out.println("Modificando...");
            if (listContratosModificados.get(i).getTipocotizante().getSecuencia() == null) {
                listContratosModificados.get(i).setTipocotizante(null);
                persistenciaContratos.editar(listContratosModificados.get(i));
            } else {
                persistenciaContratos.editar(listContratosModificados.get(i));
            }
        }
    }

    @Override
    public void borrarConceptos(List<Contratos> listaContratos) {
        for (int i = 0; i < listaContratos.size(); i++) {
            System.out.println("Borrando...");
            if (listaContratos.get(i).getTipocotizante().getSecuencia() == null) {
                listaContratos.get(i).setTipocotizante(null);
                persistenciaContratos.borrar(listaContratos.get(i));
            } else {
                persistenciaContratos.borrar(listaContratos.get(i));
            }
        }
    }

    @Override
    public void crearConceptos(List<Contratos> listaContratos) {
        for (int i = 0; i < listaContratos.size(); i++) {
            System.out.println("Creando...");
            if (listaContratos.get(i).getTipocotizante().getSecuencia() == null) {
                listaContratos.get(i).setTipocotizante(null);
                persistenciaContratos.crear(listaContratos.get(i));
            } else {
                persistenciaContratos.crear(listaContratos.get(i));
            }
        }
    }

    @Override
    public void reproducirContrato(Short codigoOrigen, Short codigoDestino) {
        persistenciaContratos.reproducirContrato(codigoOrigen, codigoDestino);
    }
}
