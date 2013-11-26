/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.SectoresEconomicos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaSectoresEconomicosInterface {

    public void crear(SectoresEconomicos sectoresEconomicos);

    public void editar(SectoresEconomicos sectoresEconomicos);

    public void borrar(SectoresEconomicos sectoresEconomicos);

    public SectoresEconomicos buscarSectorEconomico(Object id);

    public List<SectoresEconomicos> buscarSectoresEconomicos();

    public SectoresEconomicos buscarSectoresEconomicosSecuencia(BigInteger secuencia);
}
