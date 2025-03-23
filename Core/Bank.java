public class Bank {
    Person atCounter;
    private int[] chipsDupe; //copy of Person array, used to modify person array
    private int[] chipsColorsDupe;
    private int[8] chipValues = {1, 2, 5, 10, 20, 25, 50, 100};
    /*White chips – $1.
    Yellow chips – $2 (rarely used nowadays)
    Red chips – $5.
    Blue chips – $10.
    Gray chips – $20.
    Green chips – $25.
    Orange chips – $50.
    Black chips – $100. */

    public Bank(Person p){
        atCounter = p;
        chipsDupe = atCounter.getChips();
        chipsColorsDupe = atCounter.getChipColors();
    }

    public void moneyToChips(int x) throws InsufficientFunds{ //updates array of chips with the money inputted as chips
        if(atCounter.getMoney() < x){
            throw new InsufficientFunds("Invalid balance. You have: $" + atCounter.getMoney() + " and tried to get $" + x + " worth of chips.");
        }
        int toConvert = x; //money left to convert
        int tmp = 0; //tmp used to check divisibility, if the chip can be added, highest denom to lowest

        for(int i = 7; i >=0; i--){
            tmp = toConvert / chipValues[i]; //should truncate because of integer division
            if(tmp >= 1){
                chipsDupe[i] = chipsDupe[i] + tmp;
                toConvert -= tmp * chipsValues[i];
            }
        }

        atCounter.setChips(chipsDupe); //sanity check, most likely redundant
        atCounter.setMoney(0);
    }

    public void chipsToMoney(){ //only cashes out ALL your chips
        int sum = 0; //toCashOut
        for(int i = 0; i < chipsDupe.length; i++){
            sum += chipsDupe[i]*chipsValues[i];
            chipsDupe[i] = 0;
        }

        atcounter.setChips(chipsDupe);
        atCounter.setMoney(sum);
    }
}
