/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Causasausentismos;
import Entidades.Clasesausentismos;
import Entidades.Empleados;
import Entidades.Ibcs;
import Entidades.Soausentismos;
import Entidades.Tiposausentismos;
import java.math.BigInteger;
import java.util.List;

public interface AdministrarSoausentismosInterface {

    public List<Soausentismos> ausentismosEmpleado(BigInteger secuenciaEmpleado);

    public List<Empleados> lovEmpleados();

    public List<Tiposausentismos> lovTiposAusentismos();

    public List<Clasesausentismos> lovClasesAusentismos();
    
    public List<Causasausentismos> lovCausasAusentismos();
    
    public List<Ibcs> empleadosIBCS(BigInteger secuenciaEmpleado);
}
