package objects;

public class Pays
{
    private String nomPays;
    private String capital;
    private String drapeau;
    private String continent;

    public Pays(String nomPays, String capital, String drapeau, String continent) {
        this.nomPays = nomPays;
        this.capital = capital;
        this.drapeau = drapeau;
        this.continent = continent;
    }

    public String getNomPays() {
        return nomPays;
    }

    public String getDrapeau() {
        return drapeau;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "nomPays='" + nomPays + '\'' +
                ", capital='" + capital + '\'' +
                ", drapeau='" + drapeau + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }

    public String getCapital() {
        return capital;
    }

    public String getContinent() {
        return continent;
    }
}