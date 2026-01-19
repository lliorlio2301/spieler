package dhsn.verwaltung.spieler.model.domain;

public enum Position {
    TORWART("Torwart"),
    ABWEHRSPIELER("Abwehrspieler"),
    MITTELFELD("Mittelfeld"),
    FLUEGELSPIELER("Flüggelpieler"),
    SPIELMACHER("Spielmacher"),
    MITTELSTUERMER("Mittelstürmer");

    private final String bezeichnung;
    
    Position (String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
