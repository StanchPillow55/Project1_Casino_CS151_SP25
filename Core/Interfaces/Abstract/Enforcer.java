public abstract class Enforcer { //enforces functionality, cannot exceed 1 million net earnings, cannot exceed 5 drinks, must terminate the program if Scanner input.toUpperCase() is "EXIT"
    public boolean checkExit(); //for any given input, if input.toUpperCase().equals("EXIT"), terminate the program
    public void countInstances(); //check if # instances for any given class exceeds 100, if so, terminate (may change to boolean, throw exception)
    public void checkNetEarnings(); //check if net earnings exceed 1 million, if so, terminate
    public void checkInebriation(); //check if inebriation exceeds 5 drinks 
}
