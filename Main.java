public class Main {
    public static void main(String[] args) {
        Playfair pf1 = new Playfair("Disturbance", "Sail");
        Playfair pf2 = new Playfair("Aspect", "Level");
        Playfair pf3 = new Playfair("Mention", "Heal");
        Playfair pf4 = new Playfair("Incentive", "Franchise");

        System.out.println(pf1.encrypt());
        System.out.println(pf2.encrypt());
        System.out.println(pf3.encrypt());
        System.out.println(pf4.encrypt());
    }
}