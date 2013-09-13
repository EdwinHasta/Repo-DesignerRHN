/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CARGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargos.findAll", query = "SELECT c FROM Cargos c")})
public class Cargos implements Serializable {
    @OneToMany(mappedBy = "cargo")
    private Collection<Evalconvocatorias> evalconvocatoriasCollection;
    @OneToMany(mappedBy = "cargo")
    private Collection<Encargaturas> encargaturasCollection;
    @OneToMany(mappedBy = "pryCargoproyecto")
    private Collection<VigenciasProyectos> vigenciasProyectosCollection;
    @OneToMany(mappedBy = "cargo")
    private Collection<HVHojasDeVida> hVHojasDeVidaCollection;

    @OneToMany(mappedBy = "cargo")
    private Collection<SolucionesNodos> solucionesnodosCollection;
    @OneToMany(mappedBy = "cargofirmaconstancia")
    private Collection<DetallesEmpresas> detallesempresasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "SUELDOMAXIMO")
    private BigDecimal sueldomaximo;
    @Column(name = "SUELDOMINIMO")
    private BigDecimal sueldominimo;
    @Size(max = 1)
    @Column(name = "TURNOROTATIVO")
    private String turnorotativo;
    @Column(name = "SUELDOMERCADO")
    private BigDecimal sueldomercado;
    @Size(max = 1)
    @Column(name = "JEFE")
    private String jefe;
    @Size(max = 20)
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;
    @JoinColumn(name = "PROCESOPRODUCTIVO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private ProcesosProductivos procesoproductivo;
    @JoinColumn(name = "GRUPOVIATICO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private GruposViaticos grupoviatico;
    @JoinColumn(name = "GRUPOSALARIAL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private GruposSalariales gruposalarial;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private Collection<VigenciasCargos> vigenciascargosCollection;

    public Cargos() {
    }

    public Cargos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Cargos(BigInteger secuencia, String nombre) {
        this.secuencia = secuencia;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        if (nombre == null) {
            nombre = " ";
            return nombre;
        } else {
            return nombre;
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSueldomaximo() {
        return sueldomaximo;
    }

    public void setSueldomaximo(BigDecimal sueldomaximo) {
        this.sueldomaximo = sueldomaximo;
    }

    public BigDecimal getSueldominimo() {
        return sueldominimo;
    }

    public void setSueldominimo(BigDecimal sueldominimo) {
        this.sueldominimo = sueldominimo;
    }

    public String getTurnorotativo() {
        return turnorotativo;
    }

    public void setTurnorotativo(String turnorotativo) {
        this.turnorotativo = turnorotativo;
    }

    public BigDecimal getSueldomercado() {
        return sueldomercado;
    }

    public void setSueldomercado(BigDecimal sueldomercado) {
        this.sueldomercado = sueldomercado;
    }

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public ProcesosProductivos getProcesoproductivo() {
        return procesoproductivo;
    }

    public void setProcesoproductivo(ProcesosProductivos procesoproductivo) {
        this.procesoproductivo = procesoproductivo;
    }

    public GruposViaticos getGrupoviatico() {
        return grupoviatico;
    }

    public void setGrupoviatico(GruposViaticos grupoviatico) {
        this.grupoviatico = grupoviatico;
    }

    public GruposSalariales getGruposalarial() {
        return gruposalarial;
    }

    public void setGruposalarial(GruposSalariales gruposalarial) {
        this.gruposalarial = gruposalarial;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    @XmlTransient
    public Collection<VigenciasCargos> getVigenciascargosCollection() {
        return vigenciascargosCollection;
    }

    public void setVigenciascargosCollection(Collection<VigenciasCargos> vigenciascargosCollection) {
        this.vigenciascargosCollection = vigenciascargosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargos)) {
            return false;
        }
        Cargos other = (Cargos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cargos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<DetallesEmpresas> getDetallesempresasCollection() {
        return detallesempresasCollection;
    }

    public void setDetallesempresasCollection(Collection<DetallesEmpresas> detallesempresasCollection) {
        this.detallesempresasCollection = detallesempresasCollection;
    }

    @XmlTransient
    public Collection<SolucionesNodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<SolucionesNodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<HVHojasDeVida> getHVHojasDeVidaCollection() {
        return hVHojasDeVidaCollection;
    }

    public void setHVHojasDeVidaCollection(Collection<HVHojasDeVida> hVHojasDeVidaCollection) {
        this.hVHojasDeVidaCollection = hVHojasDeVidaCollection;
    }

    @XmlTransient
    public Collection<Encargaturas> getEncargaturasCollection() {
        return encargaturasCollection;
    }

    public void setEncargaturasCollection(Collection<Encargaturas> encargaturasCollection) {
        this.encargaturasCollection = encargaturasCollection;
    }

    @XmlTransient
    public Collection<VigenciasProyectos> getVigenciasProyectosCollection() {
        return vigenciasProyectosCollection;
    }

    public void setVigenciasProyectosCollection(Collection<VigenciasProyectos> vigenciasProyectosCollection) {
        this.vigenciasProyectosCollection = vigenciasProyectosCollection;
    }

    public Collection<Evalconvocatorias> getEvalconvocatoriasCollection() {
        return evalconvocatoriasCollection;
    }

    public void setEvalconvocatoriasCollection(Collection<Evalconvocatorias> evalconvocatoriasCollection) {
        this.evalconvocatoriasCollection = evalconvocatoriasCollection;
    }
}
