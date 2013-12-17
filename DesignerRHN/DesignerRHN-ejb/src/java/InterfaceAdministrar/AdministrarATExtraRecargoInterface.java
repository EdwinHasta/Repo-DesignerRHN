/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Conceptos;
import Entidades.Contratos;
import Entidades.DetallesExtrasRecargos;
import Entidades.ExtrasRecargos;
import Entidades.TiposDias;
import Entidades.TiposJornadas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarATExtraRecargoInterface {

    public List<ExtrasRecargos> listExtrasRecargos();

    public void crearExtrasRecargos(List<ExtrasRecargos> listaER);

    public void editarExtrasRecargos(List<ExtrasRecargos> listaER);

    public void borrarExtrasRecargos(List<ExtrasRecargos> listaER);

    public List<DetallesExtrasRecargos> listDetallesExtrasRecargos(BigInteger secuencia);

    public void crearDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER);

    public void editarDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER);

    public void borrarDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER);

    public List<TiposDias> listTiposDias();

    public List<TiposJornadas> listTiposJornadas();

    public List<Contratos> listContratos();

    public List<Conceptos> listConceptos();

}
