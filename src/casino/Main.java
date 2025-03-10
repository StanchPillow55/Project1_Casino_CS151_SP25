package casino;

public class Main {
    public static void main(String[] args) {
        Person p = new Person("Govil", 100);
        SportsBetting sb = new SportsBetting();
        
        sb.play(p);  

        System.out.println("Remaining chips: " + p.getChips());
    }
}
