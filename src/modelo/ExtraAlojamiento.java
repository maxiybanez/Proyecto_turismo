
package modelo;

import java.util.Objects;


public class ExtraAlojamiento {
    
    private int idExtraAlojamiento;
    private Alojamiento alojameinto;
    private String tipoMenu;
    private float costo;
    private boolean activo;

    
    
    public ExtraAlojamiento() {
    
    }

    public ExtraAlojamiento(Alojamiento alojameinto, String tipoMenu, float costo, boolean activo) {
        this.alojameinto = alojameinto;
        this.tipoMenu = tipoMenu;
        this.costo = costo;
        this.activo = activo;
    }

    public ExtraAlojamiento(int idExtraAlojamiento, Alojamiento alojameinto, String tipoMenu, float costo, boolean activo) {
        this.idExtraAlojamiento = idExtraAlojamiento;
        this.alojameinto = alojameinto;
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

    public Alojamiento getAlojameinto() {
        return alojameinto;
    }

    public void setAlojameinto(Alojamiento alojameinto) {
        this.alojameinto = alojameinto;
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
        int hash = 5;
        hash = 47 * hash + this.idExtraAlojamiento;
        hash = 47 * hash + Objects.hashCode(this.alojameinto);
        hash = 47 * hash + Objects.hashCode(this.tipoMenu);
        hash = 47 * hash + Float.floatToIntBits(this.costo);
        hash = 47 * hash + (this.activo ? 1 : 0);
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
        if (!Objects.equals(this.alojameinto, other.alojameinto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoMenu +"   -\t $"+ costo ;
    }

    
    
    
    
    
    
    
    
    
    
}  