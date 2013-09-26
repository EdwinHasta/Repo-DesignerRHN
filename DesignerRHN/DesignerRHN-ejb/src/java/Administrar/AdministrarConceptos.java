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

    @Override
    public void modificar(List<Conceptos> listConceptosModificados) {
        for (int i = 0; i < listConceptosModificados.size(); i++) {
            System.out.println("Modificando...");
            if (listConceptosModificados.get(i).getTercero().getSecuencia() == null) {
                listConceptosModificados.get(i).setTercero(null);
                persistenciaConceptos.editar(listConceptosModificados.get(i));
            } else {
                persistenciaConceptos.editar(listConceptosModificados.get(i));
            }
        }
    }

    @Override
    public void borrar(Conceptos concepto) {
        persistenciaConceptos.borrar(concepto);
    }

    @Override
    public void crear(Conceptos conceptos) {
        persistenciaConceptos.crear(conceptos);
    }
}
