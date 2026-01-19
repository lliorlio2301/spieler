package dhsn.verwaltung.spieler.model.identity;

public enum Role {
   ROLE_SUPER("SuperUser"),
   ROLE_ADMIN("Admin"),
   ROLE_SPIELER("Spieler");

   private final String name; 

   Role(String name){
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
