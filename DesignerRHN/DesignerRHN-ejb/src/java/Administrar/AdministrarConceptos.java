package Administrar;

import Entidades.Conceptos;
import Entidades.Terceros;
import Entidades.Unidades;
import InterfaceAdministrar.AdministrarConceptosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaUnidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarConceptos implements AdministrarConceptosInterface {

    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    @EJB
    PersistenciaUnidadesInterface persistenciaUnidades;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;

    @Override
    public List<Conceptos> conceptosEmpresa(BigInteger secEmpresa) {
        return persistenciaConceptos.conceptosEmpresa(secEmpresa);
    }

    @Override
    public List<Unidades> lovUnidades() {
        return persistenciaUnidades.lovUnidades();
    }

    @Override
    public List<Terceros> lovTerceros(BigInteger secEmpresa) {
        return persistenciaTerceros.lovTerceros(secEmpresa);
    }
}
