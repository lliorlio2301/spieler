package dhsn.verwaltung.spieler.model.identity;

public enum Role {
   ROLE_SUPER("SuperUser"),
   ROLE_BENUTZER("Normaler Benutzer"),
   ROLE_ADMIN("Admin");

   private final String name; 

   Role(String name){
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
