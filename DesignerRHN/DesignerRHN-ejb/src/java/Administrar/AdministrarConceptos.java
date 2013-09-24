package Administrar;

import Entidades.Conceptos;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.Unidades;
import InterfaceAdministrar.AdministrarConceptosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
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
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

    @Override
    public List<Conceptos> conceptosEmpresa(BigInteger secEmpresa) {
        return persistenciaConceptos.conceptosEmpresa(secEmpresa);
    }
    
    @Override
    public List<Empresas> listadoEmpresas() {
        return persistenciaEmpresas.buscarEmpresas();
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
