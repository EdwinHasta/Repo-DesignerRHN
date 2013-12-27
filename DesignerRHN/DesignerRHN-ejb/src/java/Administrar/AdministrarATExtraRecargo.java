package Administrar;

import Entidades.Conceptos;
import Entidades.Contratos;
import Entidades.DetallesExtrasRecargos;
import Entidades.ExtrasRecargos;
import Entidades.TiposDias;
import Entidades.TiposJornadas;
import InterfaceAdministrar.AdministrarATExtraRecargoInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaDetallesExtrasRecargosInterface;
import InterfacePersistencia.PersistenciaExtrasRecargosInterface;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
import InterfacePersistencia.PersistenciaTiposJornadasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarATExtraRecargo implements AdministrarATExtraRecargoInterface {

    //////////////// Persistencias Tablas ///////////////
    @EJB
    PersistenciaExtrasRecargosInterface persistenciaExtrasRecargos;
    @EJB
    PersistenciaDetallesExtrasRecargosInterface persistenciaDetallesExtrasRecargos;

    //////////////// Listas Valores Tablas ///////////////
    @EJB
    PersistenciaTiposDiasInterface persistenciaTiposDias;
    @EJB
    PersistenciaTiposJornadasInterface persistenciaTiposJornadas;
    @EJB
    PersistenciaContratosInterface persistenciaContratos;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;

    @Override
    public List<ExtrasRecargos> listExtrasRecargos() {
        try {
            List<ExtrasRecargos> lista = persistenciaExtrasRecargos.buscarExtrasRecargos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listExtrasRecargos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearExtrasRecargos(List<ExtrasRecargos> listaER) {
        try {
            for (int i = 0; i < listaER.size(); i++) {
                persistenciaExtrasRecargos.crear(listaER.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearExtrasRecargos Admi : " + e.toString());
        }
    }

    @Override
    public void editarExtrasRecargos(List<ExtrasRecargos> listaER) {
        try {
            for (int i = 0; i < listaER.size(); i++) {
                persistenciaExtrasRecargos.editar(listaER.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarExtrasRecargos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarExtrasRecargos(List<ExtrasRecargos> listaER) {
        try {
            for (int i = 0; i < listaER.size(); i++) {
                persistenciaExtrasRecargos.borrar(listaER.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarExtrasRecargos Admi : " + e.toString());
        }
    }

    @Override
    public List<DetallesExtrasRecargos> listDetallesExtrasRecargos(BigInteger secuencia) {
        try {
            List<DetallesExtrasRecargos> lista = persistenciaDetallesExtrasRecargos.buscaDetallesExtrasRecargosPorSecuenciaExtraRecargo(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listDetallesExtrasRecargos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER) {
        try {
            for (int i = 0; i < listaDER.size(); i++) {
                persistenciaDetallesExtrasRecargos.crear(listaDER.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearDetallesExtrasRecargos Admi : " + e.toString());
        }
    }

    @Override
    public void editarDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER) {
        try {
            for (int i = 0; i < listaDER.size(); i++) {
                persistenciaDetallesExtrasRecargos.editar(listaDER.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarDetallesExtrasRecargos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER) {
        try {
            for (int i = 0; i < listaDER.size(); i++) {
                persistenciaDetallesExtrasRecargos.borrar(listaDER.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarDetallesExtrasRecargos Admi : " + e.toString());
        }
    }

    @Override
    public List<TiposDias> listTiposDias() {
        try {
            List<TiposDias> lista = persistenciaTiposDias.buscarTiposDias();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listTiposDias Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposJornadas> listTiposJornadas() {
        try {
            List<TiposJornadas> lista = persistenciaTiposJornadas.buscarTiposJornadas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listTiposJornadas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Contratos> listContratos() {
        try {
            List<Contratos> lista = persistenciaContratos.buscarContratos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listContratos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Conceptos> listConceptos() {
        try {
            List<Conceptos> lista = persistenciaConceptos.buscarConceptos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listConceptos Admi : " + e.toString());
            return null;
        }
    }

}
