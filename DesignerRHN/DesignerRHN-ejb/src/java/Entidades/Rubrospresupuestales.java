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
@Table(name = "RUBROSPRESUPUESTALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubrospresupuestales.findAll", query = "SELECT r FROM Rubrospresupuestales r")})
public class Rubrospresupuestales implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "PLA_TIPRES")
    private String plaTipres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PLA_GRUPOS")
    private String plaGrupos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PLA_CONCEP")
    private String plaConcep;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PLA_ITEMES")
    private short plaItemes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ARE_CODIGO")
    private short areCodigo;
    @Size(max = 2)
    @Column(name = "CCO_ACTIVI")
    private String ccoActivi;
    @Size(max = 1)
    @Column(name = "MANEJANIT")
    private String manejanit;
    @Size(max = 10)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "CODIGOALTERNATIVO")
    private Long codigoalternativo;
    @OneToMany(mappedBy = "rubropresupuestal")
    private Collection<Cuentas> cuentasCollection;
    @OneToMany(mappedBy = "rubropresupuestal")
    private Collection<TiposEntidades> tiposentidadesCollection;
    @JoinColumn(name = "GRUPOTIPOCC", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private GruposTiposCC grupotipocc;
    @JoinColumn(name = "CUENTA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cuentas cuenta;

    public Rubrospresupuestales() {
    }

    public Rubrospresupuestales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Rubrospresupuestales(BigInteger secuencia, String plaTipres, String plaGrupos, String plaConcep, short plaItemes, short areCodigo) {
        this.secuencia = secuencia;
        this.plaTipres = plaTipres;
        this.plaGrupos = plaGrupos;
        this.plaConcep = plaConcep;
        this.plaItemes = plaItemes;
        this.areCodigo = areCodigo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPlaTipres() {
        return plaTipres;
    }

    public void setPlaTipres(String plaTipres) {
        this.plaTipres = plaTipres;
    }

    public String getPlaGrupos() {
        return plaGrupos;
    }

    public void setPlaGrupos(String plaGrupos) {
        this.plaGrupos = plaGrupos;
    }

    public String getPlaConcep() {
        return plaConcep;
    }

    public void setPlaConcep(String plaConcep) {
        this.plaConcep = plaConcep;
    }

    public short getPlaItemes() {
        return plaItemes;
    }

    public void setPlaItemes(short plaItemes) {
        this.plaItemes = plaItemes;
    }

    public short getAreCodigo() {
        return areCodigo;
    }

    public void setAreCodigo(short areCodigo) {
        this.areCodigo = areCodigo;
    }

    public String getCcoActivi() {
        return ccoActivi;
    }

    public void setCcoActivi(String ccoActivi) {
        this.ccoActivi = ccoActivi;
    }

    public String getManejanit() {
        return manejanit;
    }

    public void setManejanit(String manejanit) {
        this.manejanit = manejanit;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(Long codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    @XmlTransient
    public Collection<Cuentas> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }

    @XmlTransient
    public Collection<TiposEntidades> getTiposentidadesCollection() {
        return tiposentidadesCollection;
    }

    public void setTiposentidadesCollection(Collection<TiposEntidades> tiposentidadesCollection) {
        this.tiposentidadesCollection = tiposentidadesCollection;
    }

    public GruposTiposCC getGrupotipocc() {
        return grupotipocc;
    }

    public void setGrupotipocc(GruposTiposCC grupotipocc) {
        this.grupotipocc = grupotipocc;
    }

    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
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
        if (!(object instanceof Rubrospresupuestales)) {
            return false;
        }
        Rubrospresupuestales other = (Rubrospresupuestales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Rubrospresupuestales[ secuencia=" + secuencia + " ]";
    }
}
