package core;
public class Bar {
    private double earnings; //earnings for the bar

    public void inebriate(Person p){ //If someone plays a game, inebriate them
        p.setInebriation(p.getInebriation() + 1);
    }

    public void order(Person p) throws InsufficientFunds{ //add to earnings if a drink is called (all drinks $5), can add functinoality for diff drinks @param
        if(p.getMoney() < 5){
            throw new InsufficientFunds("Cannot afford a $5 drink.");
        }
        else{
            earnings += 5;
            inebriate(p);
        }
    }

    public double getEarnings(){
        return earnings;
    }

    public void setEarnings(double e){
        earnings = e;
    }
}
