
package modelo;

import java.util.Objects;


public class ExtraAlojamiento {
    
    int idExtraAlojamiento;
    String tipoMenu;
    float costo;
    boolean activo;

    public ExtraAlojamiento() {
    }

    public ExtraAlojamiento(String tipoMenu, float costo, boolean activo) {
        this.tipoMenu = tipoMenu;
        this.costo = costo;
        this.activo = activo;
    }

    public ExtraAlojamiento(int idExtraAlojamiento, String tipoMenu, float costo, boolean activo) {
        this.idExtraAlojamiento = idExtraAlojamiento;
        this.tipoMenu = tipoMenu;
        this.costo = costo;
        this.activo = activo;
    }

    public int getIdExtraAlojamiento() {
        return idExtraAlojamiento;
    }

    public void setIdExtraAlojamiento(int idExtraAlojamiento) {
        this.idExtraAlojamiento = idExtraAlojamiento;
    }

    public String getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(String tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.idExtraAlojamiento;
        hash = 41 * hash + Objects.hashCode(this.tipoMenu);
        hash = 41 * hash + Float.floatToIntBits(this.costo);
        hash = 41 * hash + (this.activo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExtraAlojamiento other = (ExtraAlojamiento) obj;
        if (this.idExtraAlojamiento != other.idExtraAlojamiento) {
            return false;
        }
        if (Float.floatToIntBits(this.costo) != Float.floatToIntBits(other.costo)) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.tipoMenu, other.tipoMenu)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExtraAlojamiento{" + "idExtraAlojamiento=" + idExtraAlojamiento + ", tipoMenu=" + tipoMenu + ", costo=" + costo + ", activo=" + activo + '}';
    }
    
    
    
}
