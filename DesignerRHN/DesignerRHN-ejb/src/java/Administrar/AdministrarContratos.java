package Administrar;

import Entidades.Contratos;
import Entidades.TiposCotizantes;
import InterfaceAdministrar.AdministrarContratosInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaTiposCotizantesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarContratos implements AdministrarContratosInterface {

    @EJB
    PersistenciaContratosInterface persistenciaContratos;
    @EJB
    PersistenciaTiposCotizantesInterface persistenciaTiposCotizantes;


    @Override
    public List<Contratos> contratos() {
        return persistenciaContratos.lovContratos();
    }

    @Override
    public List<TiposCotizantes> lovTiposCotizantes() {
        return persistenciaTiposCotizantes.lovTiposCotizantes();
    }

    @Override
    public void modificar(List<Contratos> listContratosModificados) {
        for (int i = 0; i < listContratosModificados.size(); i++) {
            System.out.println("Modificando...");
            if (listContratosModificados.get(i).getTipocotizante().getSecuencia() == null) {
                listContratosModificados.get(i).setTipocotizante(null);
                persistenciaContratos.borrar(listContratosModificados.get(i));
            } else {
                persistenciaContratos.editar(listContratosModificados.get(i));
            }
        }
    }

    @Override
    public void borrar(Contratos contrato) {
        persistenciaContratos.borrar(contrato);
    }

    @Override
    public void crear(Contratos contrato) {
        persistenciaContratos.crear(contrato);
    }

    @Override
    public void reproducirContrato(Short codigoOrigen, Short codigoDestino) {
        persistenciaContratos.reproducirContrato(codigoOrigen, codigoDestino);
    }
}
