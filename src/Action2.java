public class Action2 implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("Action2 got: " + msg);
    }
}
