package asia.zyq.shijing.utils;

public class EnergyAdder extends Thread {


    @Override
    public void run() {
        Integer addEnergy = 0;
        Integer maxEnergy = 0;
        //时间单位:秒
        Integer Intervals = 60;

        while (true) {

            try {
                sleep(Intervals * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            maxEnergy = Integer.parseInt(MainUtils.getInstance().getSetting("energy_max"));
            addEnergy = Integer.parseInt(MainUtils.getInstance().getSetting("energy_add"));
            Intervals = Integer.parseInt(MainUtils.getInstance().getSetting("energy_intervals"));
            MainUtils.getInstance().addUserEnergy(addEnergy,maxEnergy);
        }
    }
}

