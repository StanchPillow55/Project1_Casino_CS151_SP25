// Source code is decompiled from a .class file using FernFlower decompiler.
package casino;

public class Person {
   private String name;
   private int chips;
   private int inebriation;

   public Person(String name, int chips) {
      this.name = name;
      this.chips = chips;
   }

   public String getName() {
      return this.name;
   }

   public int getChips() {
      return this.chips;
   }

   public void setChips(int chips) {
      this.chips = chips;
   }

   public int getInebriation(){
     return inebriation;
   }

   public void setInebriation(int x){
     inebration = x;
   }
}
